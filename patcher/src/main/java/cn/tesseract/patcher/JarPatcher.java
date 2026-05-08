package cn.tesseract.patcher;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

/**
 * Applies a pipeline of patches to class entries in a JAR.
 *
 * Usage: JarPatcher &lt;input&gt; &lt;output&gt; --remap-ids &lt;rFile&gt;
 *
 * Pipeline: fixDex2JMethods (always), remapResourceIds (if --remap-ids given)
 */
public class JarPatcher {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: JarPatcher <input> <output> [--remap-ids <rFile>] [--patch p1,p2]");
            System.out.println("  Default: fixDex2JMethods[,remapResourceIds if --remap-ids]");
            System.exit(1);
        }

        Path inputJar = Paths.get(args[0]);
        Path outputJar = Paths.get(args[1]);

        Path rFile = null;
        List<String> patchNames = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            if ("--remap-ids".equals(args[i]) && i + 1 < args.length) rFile = Paths.get(args[++i]);
            else if ("--patch".equals(args[i]) && i + 1 < args.length)
                patchNames.addAll(Arrays.asList(args[++i].split(",")));
        }

        if (patchNames.isEmpty()) {
            patchNames.add("fixDex2JMethods");
            if (rFile != null) patchNames.add("remapResourceIds");
        }

        System.out.println("=== JarPatcher ===");
        System.out.println("Input:  " + inputJar);
        System.out.println("Output: " + outputJar);
        System.out.println("Patches: " + patchNames);

        Map<String, Object> context = new HashMap<>();
        List<Patch> patches = buildPipeline(patchNames, rFile, inputJar, context);

        int total = 0, patched = 0;
        try (JarInputStream jis = new JarInputStream(new BufferedInputStream(Files.newInputStream(inputJar)));
             JarOutputStream jos = new JarOutputStream(new BufferedOutputStream(Files.newOutputStream(outputJar)))) {

            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                byte[] data = readAllBytes(jis);

                if (entry.getName().endsWith(".class")) {
                    total++;
                    String cn = entry.getName().replace(".class", ""); // keep slashes
                    for (Patch p : patches) {
                        byte[] result = p.transform(cn, data);
                        if (result != null) { data = result; patched++; break; }
                    }
                }

                JarEntry out = new JarEntry(entry.getName());
                out.setTime(entry.getTime());
                jos.putNextEntry(out);
                jos.write(data);
                jos.closeEntry();
            }
        }
        System.out.println("Done. " + patched + " patched, " + total + " total.");
    }

    private static List<Patch> buildPipeline(List<String> names, Path rFile, Path inputJar, Map<String, Object> ctx)
            throws IOException {
        List<Patch> patches = new ArrayList<>();
        for (String name : names) {
            switch (name.trim()) {
                case "fixDex2JMethods":
                    FixDex2JMethodsPatch f = new FixDex2JMethodsPatch();
                    f.init(ctx);
                    patches.add(f);
                    break;
                case "remapResourceIds":
                    if (rFile == null || !Files.exists(rFile)) {
                        System.out.println("[WARN] remapResourceIds: R.txt not found, skipping");
                        break;
                    }
                    Map<Integer, Integer> idMap = ResourceIdRemapPatch.buildIdMapFromJar(rFile, inputJar);
                    if (!idMap.isEmpty()) {
                        System.out.println("  ID remap: " + idMap.size() + " entries");
                        ctx.put("resourceIdMap", idMap);
                        ResourceIdRemapPatch r = new ResourceIdRemapPatch();
                        r.init(ctx);
                        patches.add(r);
                    }
                    break;
                default:
                    System.out.println("[WARN] Unknown patch: " + name);
            }
        }
        return patches;
    }

    private static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) != -1) bos.write(buf, 0, n);
        return bos.toByteArray();
    }
}
