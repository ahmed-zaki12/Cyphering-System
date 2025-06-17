import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CipherGUI extends JFrame {
    // Components
    private JComboBox<String> cipherSelector;
    private JTextField inputText;
    private JTextField keyText;
    private JTextArea resultArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JLabel inputLabel;
    private JLabel keyLabel;
    private JLabel resultLabel;
    private JLabel cipherLabel;
    
    // New components
    private JButton clearButton;
    private JButton copyButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton historyButton;
    private JButton loadFileButton;
    private List<String> history;
    private JDialog historyDialog;

    // Constructor
    public CipherGUI() {
        // Set up the window
        setTitle("Cipher Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Initialize history
        history = new ArrayList<>();

        // Initialize components
        initializeComponents();
        
        // Set up the layout
        setupLayout();
        
        // Add action listeners
        addActionListeners();
        
        // Set up keyboard shortcuts
        setupKeyboardShortcuts();
        
        // Set initial theme
        applyTheme(false);
    }

    private void initializeComponents() {
        // Cipher selector
        String[] ciphers = {"Hill Cipher", "Caesar Cipher", "Monoalphabetic Cipher", 
                          "Vigenere Repeating Key", "Vigenere Auto Key"};
        cipherSelector = new JComboBox<>(ciphers);
        cipherLabel = new JLabel("Select Cipher:");

        // Input area
        inputLabel = new JLabel("Input Text:");
        inputText = new JTextField(20);

        // Key area
        keyLabel = new JLabel("Key:");
        keyText = new JTextField(20);

        // Result area
        resultLabel = new JLabel("Result:");
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Buttons
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");

        // New components
        clearButton = new JButton("Clear");
        copyButton = new JButton("Copy");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        historyButton = new JButton("History");
        loadFileButton = new JButton("Load File");
    }

    private void setupLayout() {
        // Use BorderLayout for the main frame
        setLayout(new BorderLayout());

        // Create panels
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout());

        // Add components to top panel
        topPanel.add(cipherLabel);
        topPanel.add(cipherSelector);

        // Add components to center panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(inputLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(inputText, gbc);
        gbc.gridx = 2;
        centerPanel.add(loadFileButton, gbc);

        // Key
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(keyLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(keyText, gbc);

        // Result
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        centerPanel.add(resultLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        centerPanel.add(new JScrollPane(resultArea), gbc);

        // Add buttons to bottom panel
        bottomPanel.add(encryptButton);
        bottomPanel.add(decryptButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(copyButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(historyButton);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupKeyboardShortcuts() {
        // Create input map and action map
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        // Encrypt shortcut (Ctrl+E)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "encrypt");
        actionMap.put("encrypt", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptButton.doClick();
            }
        });

        // Decrypt shortcut (Ctrl+D)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "decrypt");
        actionMap.put("decrypt", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptButton.doClick();
            }
        });

        // Clear shortcut (Ctrl+L)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), "clear");
        actionMap.put("clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButton.doClick();
            }
        });

        // Copy shortcut (Ctrl+C)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), "copy");
        actionMap.put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyButton.doClick();
            }
        });
    }

    private void applyTheme(boolean dark) {
        Color bgColor = dark ? new Color(43, 43, 43) : UIManager.getColor("Panel.background");
        Color fgColor = dark ? Color.WHITE : Color.BLACK;
        
        // Apply theme to components
        setBackground(bgColor);
        getContentPane().setBackground(bgColor);
        
        // Update text components
        inputText.setBackground(dark ? new Color(60, 60, 60) : Color.WHITE);
        inputText.setForeground(fgColor);
        keyText.setBackground(dark ? new Color(60, 60, 60) : Color.WHITE);
        keyText.setForeground(fgColor);
        resultArea.setBackground(dark ? new Color(60, 60, 60) : Color.WHITE);
        resultArea.setForeground(fgColor);
        
        // Update labels
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(fgColor);
            }
        }
    }

    private void addActionListeners() {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCipher = (String) cipherSelector.getSelectedItem();
                String input = inputText.getText();
                String key = keyText.getText();

                try {
                    String result = "";
                    switch (selectedCipher) {
                        case "Hill Cipher":
                            Hill_Cipher hill = new Hill_Cipher(input, key);
                            hill.encrypt();
                            result = hill.getCipherText();
                            break;
                        case "Caesar Cipher":
                            CaesarCipher caesar = new CaesarCipher(input, key);
                            caesar.encrypt();
                            result = caesar.getCipherText();
                            break;
                        case "Monoalphabetic Cipher":
                            MonoalphabeticCipher mono = new MonoalphabeticCipher(input, key);
                            mono.encrypt();
                            result = mono.getCipherText();
                            break;
                        case "Vigenere Repeating Key":
                            Vigenere_Reapting_Key vigenere = new Vigenere_Reapting_Key(input, key);
                            vigenere.encrypt();
                            result = vigenere.getCipherText();
                            break;
                        case "Vigenere Auto Key":
                            Vigenere_Auto_Key autoKey = new Vigenere_Auto_Key(input, key);
                            autoKey.encrypt();
                            result = autoKey.getCipherText();
                            break;
                    }
                    resultArea.setText(result);
                    addToHistory("Encrypt", input, key, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CipherGUI.this,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCipher = (String) cipherSelector.getSelectedItem();
                String input = inputText.getText();
                String key = keyText.getText();

                try {
                    String result = "";
                    switch (selectedCipher) {
                        case "Hill Cipher":
                            Hill_Cipher hill = new Hill_Cipher(input, key);
                            hill.decrypt();
                            result = hill.getPlainText();
                            break;
                        case "Caesar Cipher":
                            CaesarCipher caesar = new CaesarCipher(input, key);
                            caesar.decrypt();
                            result = caesar.getPlainText();
                            break;
                        case "Monoalphabetic Cipher":
                            MonoalphabeticCipher mono = new MonoalphabeticCipher(input, key);
                            mono.decrypt();
                            result = mono.getPlainText();
                            break;
                        case "Vigenere Repeating Key":
                            Vigenere_Reapting_Key vigenere = new Vigenere_Reapting_Key(input, key);
                            vigenere.decrypt();
                            result = vigenere.getPlainText();
                            break;
                        case "Vigenere Auto Key":
                            Vigenere_Auto_Key autoKey = new Vigenere_Auto_Key(input, key);
                            autoKey.decrypt();
                            result = autoKey.getPlainText();
                            break;
                    }
                    resultArea.setText(result);
                    addToHistory("Decrypt", input, key, result);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CipherGUI.this,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Clear button
        clearButton.addActionListener(e -> {
            inputText.setText("");
            keyText.setText("");
            resultArea.setText("");
        });

        // Copy button
        copyButton.addActionListener(e -> {
            String result = resultArea.getText();
            if (!result.isEmpty()) {
                StringSelection selection = new StringSelection(result);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(this, "Result copied to clipboard!");
            }
        });

        // Save button
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                    writer.println("Cipher: " + cipherSelector.getSelectedItem());
                    writer.println("Input: " + inputText.getText());
                    writer.println("Key: " + keyText.getText());
                    writer.println("Result: " + resultArea.getText());
                    JOptionPane.showMessageDialog(this, "File saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Load button
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Cipher: ")) {
                            String cipher = line.substring(8);
                            cipherSelector.setSelectedItem(cipher);
                        } else if (line.startsWith("Input: ")) {
                            inputText.setText(line.substring(7));
                        } else if (line.startsWith("Key: ")) {
                            keyText.setText(line.substring(5));
                        } else if (line.startsWith("Result: ")) {
                            resultArea.setText(line.substring(8));
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Load File button
        loadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Text Files", "txt"));
            
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    // Read the file content
                    StringBuilder content = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                    }
                    
                    // Set the content to the input field
                    inputText.setText(content.toString().trim());
                    
                    // Show success message
                    JOptionPane.showMessageDialog(this,
                        "File loaded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                        
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error reading file: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // History button
        historyButton.addActionListener(e -> showHistoryDialog());
    }

    private void showHistoryDialog() {
        if (historyDialog == null) {
            historyDialog = new JDialog(this, "History", true);
            historyDialog.setSize(400, 300);
            historyDialog.setLocationRelativeTo(this);

            JTextArea historyArea = new JTextArea();
            historyArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(historyArea);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> historyDialog.dispose());
            panel.add(closeButton, BorderLayout.SOUTH);

            historyDialog.add(panel);
        }

        // Update history content
        JTextArea historyArea = (JTextArea) ((JScrollPane) ((JPanel) historyDialog.getContentPane().getComponent(0)).getComponent(0)).getViewport().getView();
        StringBuilder sb = new StringBuilder();
        for (String entry : history) {
            sb.append(entry).append("\n\n");
        }
        historyArea.setText(sb.toString());

        historyDialog.setVisible(true);
    }

    private void addToHistory(String operation, String input, String key, String result) {
        String entry = String.format("Operation: %s\nCipher: %s\nInput: %s\nKey: %s\nResult: %s",
                operation,
                cipherSelector.getSelectedItem(),
                input,
                key,
                result);
        history.add(entry);
    }
} 