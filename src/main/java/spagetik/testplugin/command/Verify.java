package spagetik.testplugin.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import spagetik.testplugin.DisVerify;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Verify extends AbstractCommand {

    ArrayList<UUID> uuids = new ArrayList<>();

    public Verify() {
        super("disverify");
    }

    private void sendSuccessMsg(@NotNull Player player, int code) {
        String msg = DisVerify.getInstance().getConfig().getString("messages.successful");
        assert msg != null;
        if (msg.contains("{player}")) {
            msg = msg.replaceAll("\\{player}", player.getName());
        }
        if (msg.contains("{code}")) {
            msg = msg.replaceAll("\\{code}", String.valueOf(code));
        }
        TextComponent message = new TextComponent(msg);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(code)));
        player.sendMessage(message);
    }

    private void sendUnsuccessfulMsg(@NotNull Player player) {
        String msg = DisVerify.getInstance().getConfig().getString("messages.unsuccessful");
        assert msg != null;
        player.sendMessage(msg);
    }

    private void sendYouHaveMsg(@NotNull Player player) {
        String msg = DisVerify.getInstance().getConfig().getString("messages.havecode");
        assert msg != null;
        player.sendMessage(msg);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (!(uuids.contains(uuid))) {
                int code = new Random().nextInt((999999 - 100000) + 1) + 100000;
                uuids.add(uuid);
                if (DisVerify.getInstance().db.addCodeToDb(uuid, code)) {
                    sendSuccessMsg(player, code);
                }
                else {
                    sendUnsuccessfulMsg(player);
                }
                Bukkit.getScheduler().runTaskLaterAsynchronously(DisVerify.getInstance(), () -> {
                    uuids.remove(uuid);
                    DisVerify.getInstance().db.removeCodeFromDb(code);
                }, 1300);
            }
            else {
                sendYouHaveMsg(player);
            }
        }
    }
}
