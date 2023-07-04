package texteditor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5098077681666028778L;
	private JTextPane textPane;
    private JButton cutButton;

    public Test() {
        super("Text Pane Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        textPane = new JTextPane();
        cutButton = new JButton("Cut");
        cutButton.addActionListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
        panel.add(cutButton, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cutButton) {
            String selectedText = textPane.getSelectedText();
            if (selectedText != null) {
                StringSelection stringSelection = new StringSelection(selectedText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                textPane.replaceSelection("");
            }
        }
    }

    public static void main(String[] args) {
        Test example = new Test();
        example.setVisible(true);
    }
}
