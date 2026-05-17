package dev.rinchan.paperplane.client;

import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.rinchan.paperplane.PaperPlaneNetworking;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TeleportRequestScreen extends BaseScreen {
    private final UUID requestId;
    private final String requesterName;
    private final boolean enderPlane;

    public TeleportRequestScreen(UUID requestId, String requesterName, boolean enderPlane) {
        this.requestId = requestId;
        this.requesterName = requesterName;
        this.enderPlane = enderPlane;
        setSize(360, 120);
    }

    @Override
    public void addWidgets() {
        SimpleTextButton accept = SimpleTextButton.accept(this, mouseButton -> {
            PaperPlaneNetworking.answerRequest(requestId, true);
            closeGui(false);
        });
        accept.setTitle(Component.translatable("screen.paper_plane.accept"));
        accept.setPosAndSize(16, 86, 150, 20);
        add(accept);

        SimpleTextButton deny = SimpleTextButton.cancel(this, mouseButton -> {
            PaperPlaneNetworking.answerRequest(requestId, false);
            closeGui(false);
        });
        deny.setTitle(Component.translatable("screen.paper_plane.deny"));
        deny.setPosAndSize(194, 86, 150, 20);
        add(deny);
    }

    @Override
    public void alignWidgets() {
        setPos((getScreen().getGuiScaledWidth() - width) / 2, (getScreen().getGuiScaledHeight() - height) / 2);
    }

    @Override
    public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
        super.drawBackground(graphics, theme, x, y, w, h);
        theme.drawGui(graphics, x, y, w, h, getWidgetType());
        theme.drawString(graphics, Component.translatable("screen.paper_plane.request_title"), x + 12, y + 12);
        theme.drawString(graphics, Component.translatable(enderPlane ? "screen.paper_plane.request_body_ender" : "screen.paper_plane.request_body", requesterName), x + 12, y + 36);
    }
}
