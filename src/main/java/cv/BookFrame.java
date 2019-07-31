package cv;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.petebevin.markdown.MarkdownProcessor;

public class BookFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String html = "";
	private JEditorPane HTMLPanel;

	private BookFrame() {
		super("book");
		setSize(500, 500);
		setVisible(true);

		HTMLPanel = new JEditorPane("text/html", html);
		HTMLPanel.setEditable(false);
		HTMLPanel.setPreferredSize(new Dimension(200, 150));
		JScrollPane scrollPane = new JScrollPane(HTMLPanel);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}

	public void update() {
		try {
			AstahAPI api = AstahAPI.getAstahAPI();
			IDiagramViewManager viewMan = api.getProjectAccessor().getViewManager().getDiagramViewManager();
			IPresentation[] selectedPs = viewMan.getSelectedPresentations();
			MarkdownProcessor mark2html = new MarkdownProcessor();
			html = mark2html.markdown(getMarkDown(selectedPs));
			HTMLPanel.setText(html);
		} catch (Exception e) {
			// TOO: handle exception
		}
	}

	private String getMarkDown(IPresentation[] selectedPs) {
		if (selectedPs.length > 0) {
			IElement model = selectedPs[0].getModel();
			if (model instanceof INamedElement) {
				return ((INamedElement) model).getDefinition();
			}
		}
		return null;
	}

	public static BookFrame getInstance() {
		return BookFrameHolder.INSTANCE;
	}

	public static class BookFrameHolder {
		public static BookFrame INSTANCE = new BookFrame();
	}
}
