package Components;

// Class Component
// Every object in the netlist is a component

public abstract class Component {

    protected String name = "";
    protected Component parent = null;

    public Component(String name, Component parent) {
        if(name != null) {
            this.name = name;
        }
        this.parent = parent;
    }


    
    public String getName() {
        return name;
    }

    public String getFullName() {
        if(parent == null) {
            return name;
        }

        return parent.getFullName() + "." + name;
    }

    public Component getParent() {
        return parent;
    }
}
