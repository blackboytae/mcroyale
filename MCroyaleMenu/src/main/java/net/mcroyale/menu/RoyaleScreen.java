package net.mcroyale.menu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.*;

public final class RoyaleScreen extends Screen {
    private static final int BLUE = 0xFF54BFFF, TEXT = 0xFFD3E2FA, MUTED = 0xFF8EA4C7;
    private final LinkedHashMap<String, List<String>> groups = new LinkedHashMap<>();
    private final Set<String> enabled = new HashSet<>();
    private String search = "";
    private int scroll = 0;
    private float scale = 1f;
    public RoyaleScreen() {
        super(Text.literal("MC Royale Menu"));
        groups.put("COMBAT", Arrays.asList("Aim Assist","Anchor Macro","Auto Double Hand","Auto Crystal","Auto Hit Crystal","Auto Inv Totem","Auto Jump Reset","Auto Totem","Crystal Optimizer","Double Anchor","Elytra Swap","Hitbox","Hover Totem","Totem Offhand","Mace Swap","Spear Swap","No Hit Delay","Shield Breaker","Static Hitboxes","Trigger Bot","Mace Bomber"));
        groups.put("MISC", Arrays.asList("Auto Clicker","Auto Walk","Auto Eat","Auto Firework","Auto Log","Auto Loot","Auto Mine","Auto TP","Cords Snapper","Elytra Glide","Fast Place","Freecam","Key Pearl","Key Wind Charge","Sprint","Skin Protect","Auto Reconnect","Weather Notifier"));
        groups.put("DONUT", Arrays.asList("Anti Trap","Auction Sniper","Auto Sell","Auto Spawner Sell","Fake Pay","Item Dropper","Netherite Finder","Auto Shulker Buy","Auto Shop","Tunnel Base Finder","Fake Stats","Spawner Protect","Chunk Finder","Prime Chunk Finder","Block Entity Debug","Player Chunks"));
        groups.put("BASEFINDING", Arrays.asList("Seed Chunk Finder","Hole ESP","Light Finder","Sus Chunk Finder","Suspicious ESP"));
        groups.put("RENDER", Arrays.asList("Name Tags","Ore Sim","Fullbright","Swingspeed","Jump Circles","HUD","Player ESP","Storage ESP","Block ESP","Target HUD","Real Hitbox","Free Look","Spotify HUD","Pearl Trajectory","Mob ESP"));
        groups.put("CLIENT", Arrays.asList("Krypton+","Chat Macro","Radio","Friends","Discord Presence","Proxy"));
        groups.put("SEARCH", List.of());
        enabled.addAll(Arrays.asList("Auto Reconnect","Weather Notifier","Player Chunks","Sus Chunk Finder","Name Tags","Fullbright","Swingspeed","Jump Circles","HUD","Player ESP","Pearl Trajectory","Mob ESP","Krypton+","Discord Presence"));
    }
    @Override public boolean shouldPause() { return false; }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        scale = Math.min(1f, this.width / 1120f);
        ctx.getMatrices().push(); ctx.getMatrices().scale(scale, scale);
        int w = (int)(width / scale), h = (int)(height / scale);
        ctx.fill(0, 0, w, h, 0xA9142032);
        int x = 8, y = 14, panelW = 160, gap = 10;
        for (var entry : groups.entrySet()) {
            List<String> items = entry.getValue();
            if (entry.getKey().equals("SEARCH")) {
                panel(ctx, x, y, panelW, 62, entry.getKey());
                ctx.fill(x+7, y+28, x+panelW-7, y+52, 0xFF101B2A);
                ctx.drawTextWithShadow(textRenderer, Text.literal(search.isEmpty()?"Search...":search), x+13, y+36, MUTED);
                x += panelW + gap; continue;
            }
            List<String> visible = new ArrayList<>();
            for (String item : items) if (search.isEmpty() || item.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))) visible.add(item);
            int top = y, rowH = 23, maxRows = Math.max(1, (h - y - 20) / rowH);
            int start = Math.min(scroll, Math.max(0, visible.size()-maxRows));
            int shown = Math.min(maxRows, visible.size()-start);
            int ph = 34 + shown * rowH;
            panel(ctx, x, top, panelW, ph, entry.getKey());
            for (int i=0; i<shown; i++) {
                String item = visible.get(start+i); int ry = top+31+i*rowH;
                if (enabled.contains(item)) ctx.fill(x+4, ry-2, x+panelW-4, ry+rowH-2, 0x223DAAFF);
                ctx.drawTextWithShadow(textRenderer, Text.literal(item.toUpperCase(Locale.ROOT)), x+9, ry+4, enabled.contains(item)?BLUE:TEXT);
                int tx=x+panelW-27, ty=ry+5;
                ctx.fill(tx,ty,tx+20,ty+12,enabled.contains(item)?0xFF145A9B:0xFF263449);
                ctx.fill(enabled.contains(item)?tx+10:tx+1,ty+1,enabled.contains(item)?tx+19:tx+10,ty+11,enabled.contains(item)?BLUE:0xFF9AAAC4);
            }
            x += panelW + gap;
        }
        ctx.drawTextWithShadow(textRenderer, Text.literal("RIGHT SHIFT: CLOSE  •  CLICK TO TOGGLE  •  SEARCH: TYPE"), 12, h-17, MUTED);
        ctx.getMatrices().pop();
        super.render(ctx, mouseX, mouseY, delta);
    }
    private void panel(DrawContext ctx,int x,int y,int w,int h,String title) {
        ctx.fill(x,y,x+w,y+h,0xE9091422); ctx.fill(x,y,x+w,y+28,0xFF101D2D);
        ctx.fill(x,y,x+w,y+1,0xFF2B4566); ctx.fill(x,y+h-1,x+w,y+h,0xFF203752);
        ctx.fill(x,y,x+1,y+h,0xFF203752); ctx.fill(x+w-1,y,x+w,y+h,0xFF203752);
        ctx.drawTextWithShadow(textRenderer, Text.literal("◆  "+title),x+8,y+9,BLUE);
        ctx.fill(x+4,y+28,x+w-4,y+29,0xFF1A2A3E);
    }
    @Override public boolean mouseClicked(double mx,double my,int button) {
        if(button!=0) return super.mouseClicked(mx,my,button);
        int x=(int)(mx/scale), y=(int)(my/scale), px=8, top=14, panelW=160, gap=10, h=(int)(height/scale);
        for(var entry:groups.entrySet()) {
            if(entry.getKey().equals("SEARCH")) { if(x>=px && x<=px+panelW && y>=top+28 && y<=top+54) { search=""; } px+=panelW+gap; continue; }
            List<String> visible=new ArrayList<>(); for(String item:entry.getValue()) if(search.isEmpty()||item.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))) visible.add(item);
            int rowH=23,maxRows=Math.max(1,(h-top-20)/rowH),start=Math.min(scroll,Math.max(0,visible.size()-maxRows)),shown=Math.min(maxRows,visible.size()-start);
            if(x>=px&&x<=px+panelW&&y>=top+31&&y<top+31+shown*rowH) { int idx=(y-(top+31))/rowH+start; if(idx>=0&&idx<visible.size()) {String item=visible.get(idx); if(!enabled.add(item))enabled.remove(item);} return true; }
            px+=panelW+gap;
        }
        return true;
    }
    @Override public boolean charTyped(char chr,int modifiers) { if(!Character.isISOControl(chr)) search+=chr; return true; }
    @Override public boolean keyPressed(int keyCode,int scanCode,int modifiers) {
        if(keyCode==GLFW.GLFW_KEY_ESCAPE||keyCode==GLFW.GLFW_KEY_RIGHT_SHIFT){close();return true;}
        if(keyCode==GLFW.GLFW_KEY_BACKSPACE&&!search.isEmpty()){search=search.substring(0,search.length()-1);return true;}
        if(keyCode==GLFW.GLFW_KEY_UP) {scroll=Math.max(0,scroll-1);return true;}
        if(keyCode==GLFW.GLFW_KEY_DOWN) {scroll++;return true;}
        return super.keyPressed(keyCode,scanCode,modifiers);
    }
}
