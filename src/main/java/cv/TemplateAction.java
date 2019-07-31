package cv;

import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IEntitySelectionEvent;
import com.change_vision.jude.api.inf.view.IEntitySelectionListener;

public class TemplateAction implements IPluginActionDelegate {
	public Object run(IWindow window) throws UnExpectedException {
		BookFrame.getInstance();
		try {
			AstahAPI api = AstahAPI.getAstahAPI();
			IDiagramViewManager viewMan = api.getProjectAccessor().getViewManager().getDiagramViewManager();
			viewMan.addEntitySelectionListener(new IEntitySelectionListener() {
				@Override
				public void entitySelectionChanged(IEntitySelectionEvent e) {
					System.out.println("aaa");
					BookFrame.getInstance().update();
				}
			});			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert",
					JOptionPane.ERROR_MESSAGE);
			throw new UnExpectedException();
		}
		return null;
	}
}
