package org.gephi.toolkit.examples.nodeselection;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Node;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewMouseEvent;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.spi.PreviewMouseListener;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = PreviewMouseListener.class)
public class CustomMouseListener implements PreviewMouseListener {

    private PreviewController previewController;

    @Override
    public void mouseClicked(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
        if (previewController == null) {
            previewController = Lookup.getDefault().lookup(PreviewController.class);
        }

        for (Node node : Lookup.getDefault().lookup(GraphController.class).getGraphModel(workspace).getGraph().getNodes()) {
            if (clickingInNode(node, event)) {
                properties.putValue("display-label.node.id", node.getId());
                Logger.getLogger("").log(Level.INFO, "Node {0} clicked!", node.getLabel());
                JOptionPane.showMessageDialog(null, "Node " + node.getLabel() + " clicked!");
                event.setConsumed(true);//So the renderer is executed and the graph repainted
                previewController.refreshPreview(workspace);//So our ItemBuilderTemplate gets called and builds the new item
                return;
            }
        }

        properties.removeSimpleValue("display-label.node.id");
        event.setConsumed(true);//So the renderer is executed and the graph repainted
        previewController.refreshPreview(workspace);//So our ItemBuilderTemplate gets called and no longer builds the item
    }

    @Override
    public void mousePressed(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
    }

    @Override
    public void mouseDragged(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
    }

    @Override
    public void mouseReleased(PreviewMouseEvent event, PreviewProperties properties, Workspace workspace) {
    }

    private boolean clickingInNode(Node node, PreviewMouseEvent event) {
        float xdiff = node.x() - event.x;
        float ydiff = -node.y() - event.y;//Note that y axis is inverse for node coordinates
        float radius = node.size();

        return xdiff * xdiff + ydiff * ydiff < radius * radius;
    }
}
