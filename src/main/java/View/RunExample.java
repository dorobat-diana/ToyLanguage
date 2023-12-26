package View;

import Controller.Controller;

public class RunExample extends Command {
   String key;
   String description;
   Controller controller;
   public RunExample(String key, String description, Controller controller){
       super(key, description);
       this.controller = controller;
   }
    @Override
    public void execute() {
        try{
            controller.allStep();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
