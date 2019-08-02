package cv;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

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
			html = MarkDown2Html(getMarkDown(selectedPs));
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
	
	private String MarkDown2Html(String markDown) {
	    MutableDataSet options = new MutableDataSet();
	    options.set(Parser.FENCED_CODE_BLOCK_PARSER,false);
	    options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));
	    Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markDown);
        String viewHtml = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
	    return viewHtml;
	}

	public static BookFrame getInstance() {
		return BookFrameHolder.INSTANCE;
	}

	public static class BookFrameHolder {
		public static BookFrame INSTANCE = new BookFrame();
	}
}
