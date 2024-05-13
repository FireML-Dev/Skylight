package uk.firedev.skylight.modules.elevator;

import org.bukkit.FluidCollisionMode;
import org.bukkit.util.RayTraceResult;
import uk.firedev.daisylib.libs.commandapi.CommandAPICommand;
import uk.firedev.daisylib.utils.ItemUtils;

public class ElevatorCommand extends CommandAPICommand {

    private static ElevatorCommand instance = null;

    private ElevatorCommand() {
        super("elevator");
        withPermission("skylight.command.elevator");
        withShortDescription("Manage Elevators");
        withFullDescription("Manage Elevators");
        withSubcommands(getGiveBlockCommand(), getUnsetElevatorCommand());
        executes((sender, arguments) -> {
            ElevatorConfig.getInstance().getCommandUsageMessage().sendMessage(sender);
        });
    }

    public static ElevatorCommand getInstance() {
        if (instance == null) {
            instance = new ElevatorCommand();
        }
        return instance;
    }

    private CommandAPICommand getGiveBlockCommand() {
        return new CommandAPICommand("giveBlock")
                .executesPlayer((player, arguments) -> {
                    ItemUtils.giveItem(ElevatorManager.getInstance().getElevatorBlock(), player);
                    ElevatorConfig.getInstance().getCommandGivenMessage().sendMessage(player);
                });
    }

    private CommandAPICommand getUnsetElevatorCommand() {
        return new CommandAPICommand("unsetElevator")
                .executesPlayer((player, arguments) -> {
                    RayTraceResult traced = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5, FluidCollisionMode.NEVER, true);
                    if (traced == null || traced.getHitBlock() == null) {
                        ElevatorConfig.getInstance().getCommandInvalidMessage().sendMessage(player);
                        return;
                    }
                    Elevator elevator = new Elevator(traced.getHitBlock());
                    if (!elevator.isElevator()) {
                        ElevatorConfig.getInstance().getCommandInvalidMessage().sendMessage(player);
                        return;
                    }
                    elevator.setElevator(false);
                    ElevatorConfig.getInstance().getCommandUnregisterMessage().sendMessage(player);
                });
    }

}
