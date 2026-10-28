MC ROYALE MENU — Fabric 1.21.11
================================

WHAT THIS IS
A real Fabric client mod project for Minecraft Java 1.21.11. It recreates the dark panels, blue highlights, categories and toggles from the reference image. The toggles currently change the menu's visual state only; they do not implement cheats or gameplay modifications.

HOW TO BUILD ON WINDOWS
1. Install Java 21 (Minecraft 1.21.11 needs Java 21).
2. Make sure Fabric Loader for Minecraft 1.21.11 and Fabric API 0.141.1+1.21.11 are installed in your launcher profile.
3. Extract this ZIP to a normal folder.
4. Double-click build.bat and allow it to download Gradle and the Minecraft/Fabric build dependencies.
5. When it says SUCCESS, the JAR will be at build\libs\mcroyale-menu-1.0.0.jar.
6. Copy that JAR into %AppData%\.minecraft\mods (or the mods folder used by your launcher).
7. Launch the Fabric 1.21.11 profile and press RIGHT SHIFT to open the menu. Press RIGHT SHIFT or ESC to close it.

CONTROLS
- Right Shift: open/close menu
- Click a row: toggle its blue/gray state
- Type: filter modules by name
- Backspace: edit search text
- Up/Down: adjust the visible list position

NOTES
- Requires Minecraft Java 1.21.11, Fabric Loader and Fabric API.
- The build script downloads Gradle from the official Gradle distribution site; internet access is required on first build.
- This source project is provided because a compiled JAR cannot be built in this environment (Gradle distribution downloads are unavailable here).
