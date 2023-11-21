package view;

import controller.Controller;

public class RunExampleCommand extends Command{
    private Controller ctrl;
    public RunExampleCommand(String key, String description, Controller ctrl)
    {
        super(key, description);
        this.ctrl = ctrl;
    }
    @Override
    public void execute()
    {
        try{
            ctrl.allStep();
            System.out.println("Command executed with success!\n");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getKey() + " " + this.getDescription();
    }
}