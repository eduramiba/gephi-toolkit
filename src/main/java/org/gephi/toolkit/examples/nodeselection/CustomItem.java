package org.gephi.toolkit.examples.nodeselection;


import org.gephi.graph.api.Node;
import org.gephi.preview.api.Item;

/**
 * Basic item without properties but a node.
 */
public class CustomItem implements Item{
    private final Node node;

    public CustomItem(Node node) {
        this.node = node;
    }
    
    @Override
    public Object getSource() {
        return node;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String getType() {
        return "label.sometype";
    }

    @Override
    public <D> D getData(String key) {
        return null;
    }

    @Override
    public void setData(String key, Object value) {
    }

    @Override
    public String[] getKeys() {
        return new String[0];
    }

    @Override
    public String toString() {
        return "LabelItem{" + "node=" + node.getId() + '}';
    }
}
