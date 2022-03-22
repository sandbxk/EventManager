package gui.component;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.layout.Region;

public class SizeControlledContextMenu extends ContextMenu {

        public SizeControlledContextMenu() {
            addEventHandler(Menu.ON_SHOWING, e -> {
                Node content = getSkin().getNode();
                if (content instanceof Region) {
                    ((Region) content).setMaxHeight(getMaxHeight());
                }
            });

        }
    }

