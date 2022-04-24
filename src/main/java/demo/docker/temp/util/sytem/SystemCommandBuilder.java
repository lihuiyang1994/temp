package demo.docker.temp.util.sytem;

/**
 * lihui
 * 2022/4/23
 * SystemCommandBuilder
 *
 * @description
 */
public class SystemCommandBuilder {

    public static CommandExecutor winCommandE() {
        return null;
    }

    public static CommandExecutor linuxCommandE() {
        return new CommandExecutor(new LinuxCommand());
    }


    public static class CommandExecutor{

        private SystemCommand command;

        public CommandExecutor(SystemCommand command) {
            this.command = command;
        }
    }
}
