package net.mcroyale.menu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public final class MCRoyaleMenu implements ClientModInitializer {
    private boolean wasDown;
    @Override public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            long window = MinecraftClient.getInstance().getWindow().getHandle();
            boolean down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
            if (down && !wasDown && client.currentScreen == null) client.setScreen(new RoyaleScreen());
            wasDown = down;
        });
    }
}
