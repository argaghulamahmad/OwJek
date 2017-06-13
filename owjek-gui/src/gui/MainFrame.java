package gui;

import engine.OwJekEngine;
import engine.UserAccount;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Created by Arga Ghulam Ahmad on 5/10/2017.
 */

/**
 * Kelas ini merupakan frame program utama program OwJek GUI.
 */
public class MainFrame extends JFrame {
    private UserAccount user;
    private OwJekEngine owjekEngine;
    private String username;
    private JPanel rootPanel;
    private JPanel OwJek;
    private JTextField result1;
    private JTextField result2;
    private JTextField result3;
    private JTextField result4;
    private JTextField result5;
    private JTextField result6;
    private JTextField result7;
    private JTextField result8;
    private JTextField result9;
    private JTabbedPane tabbedPane;
    private JPanel main;
    private JPanel side;
    private JTextField startField;
    private JTextField finishField;
    private JButton goButton;
    private JButton resetButton;
    private JButton processButton;
    private JLabel startLabel;
    private JLabel finishLabel;
    private JLabel distanceLabel;
    private JLabel owjektypeLabel;
    private JLabel fixedcostLabel;
    private JLabel first2kmLabel;
    private JLabel first5kmLabel;
    private JLabel completekmLabel;
    private JLabel promoLabel;
    private JLabel prtkscostLabel;
    private JLabel totalLabel;
    private JComboBox typeCombobox;
    private JLabel greetLabel;
    private JLabel usernameLabel;
    private JLabel logoAbout;
    private JLabel sideLogo;
    private JPanel About;
    private JLabel argaLogo;
    private JTable historyTable;
    private JButton saveHistory;
    private JLabel userPicture;
    private JPanel imagePanel;
    private JPanel infoPanel;
    private JTextField usernameField;
    private JTextArea addressField;
    private JTextField fullnameField;
    private JTextField birthdateField;
    private JTextField birthlocationField;
    private JComboBox genderField;
    private JTextField occupationField;
    private JTextField emailField;
    private JLabel info3Lbl;
    private JLabel info4Lbl;
    private JLabel info5Lbl;
    private JLabel info6Lbl;
    private JLabel info7Lbl;
    private JLabel info8Lbl;
    private JLabel info9Lbl;
    private JLabel info1Lbl;
    private JLabel info2Lbl;
    private JLabel basicLbl;
    private JLabel advanceLbl;
    private JButton saveProfile;
    private JComboBox statusField;
    private JButton editProfile;
    private JLabel phonenumberLabel;
    private JTextField phonenumberField;
    private DefaultTableModel historyModel;
    private boolean resetFlag;
    private boolean start;
    private boolean finish;

    public MainFrame() {

    }

    public MainFrame(String username) throws HeadlessException {
        resetFlag = false;

        user = new UserAccount();
        owjekEngine = new OwJekEngine();
        JOptionPane.showMessageDialog(null, "Selamat anda berhasil masuk.");
        this.username = username;

        $$$setupUI$$$();
        setTitle("OwJek");
        ImageIcon icon = new ImageIcon("src/img/Asset 3.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationByPlatform(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        setContentPane(rootPanel);

        usernameLabel.setText(username);

        pack();
        setVisible(true);

        drawMap();
        createActionListener();
    }

    /**
     * Inisiasi komponen user interface pada main frame.
     */
    private void createUIComponents() {
        main = new JPanel(new GridLayout(owjekEngine.getMap().getHEIGHT(), owjekEngine.getMap().getWIDTH()));

        String[] owjekTypeArr = {"", "Regular", "Sporty", "Exclusive"};
        typeCombobox = new JComboBox(owjekTypeArr);
        typeCombobox.setSelectedIndex(0);

        result1 = new JTextField();
        result2 = new JTextField();
        result3 = new JTextField();
        result4 = new JTextField();
        result5 = new JTextField();
        result6 = new JTextField();
        result7 = new JTextField();
        result8 = new JTextField();
        result9 = new JTextField();

        result1.setEditable(false);
        result2.setEditable(false);
        result3.setEditable(false);
        result4.setEditable(false);
        result5.setEditable(false);
        result6.setEditable(false);
        result7.setEditable(false);
        result8.setEditable(false);
        result9.setEditable(false);

        sideLogo = new JLabel();
        ImageIcon sideIcon = new ImageIcon("src/img/Asset 2.png");
        sideLogo.setIcon(sideIcon);

        String[] columnNames = {
                "waktu",
                "username",
                "start",
                "finish",
                "owjek",
                "distance",
                "fixed",
                "2KMPe",
                "5KMPe",
                "KMSel",
                "Promo",
                "Prtks",
                "Total",
        };

        historyModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(historyModel);

        usernameField = new JTextField();
        usernameField.setText(username);

        String[] genderArr = {"", "MALE", "FEMALE"};
        String[] statusArr = {"", "SINGLE", "MARRIED"};
        genderField = new JComboBox(genderArr);
        statusField = new JComboBox(statusArr);
    }

    /**
     * Inisiasi action listener pada setiap widget yang dibutuhkan.
     */
    private void createActionListener() {
        resetButton.addActionListener(e -> {
            startField.setText("");
            finishField.setText("");
            typeCombobox.setSelectedIndex(0);

            result1.setText("");
            result2.setText("");
            result3.setText("");
            result4.setText("");
            result5.setText("");
            result6.setText("");
            result7.setText("");
            result8.setText("");
            result9.setText("");

            owjekEngine.getMap().initMap();
            drawMap();

            resetFlag = false;
        });

        goButton.addActionListener(e -> {
            if (startField.getText().length() == 4 && finishField.getText().length() == 4 && typeCombobox.getSelectedItem().toString().length() != 0) {
                try {
                    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                    formatRp.setCurrencySymbol("Rp. ");
                    formatRp.setMonetaryDecimalSeparator(',');
                    formatRp.setGroupingSeparator('.');
                    kursIndonesia.setDecimalFormatSymbols(formatRp);

                    double[] resultArr = owjekEngine.cmdGo(startField.getText(), finishField.getText(), typeCombobox.getSelectedItem().toString());

                    result1.setText(Double.toString(resultArr[0]) + " Km");
                    result2.setText(typeCombobox.getSelectedItem().toString());
                    result3.setText(String.format("%s", kursIndonesia.format(resultArr[1])));
                    result4.setText(String.format("%s", kursIndonesia.format(resultArr[2])));
                    result5.setText(String.format("%s", kursIndonesia.format(resultArr[3])));
                    result6.setText(String.format("%s", kursIndonesia.format(resultArr[4])));
                    result7.setText(String.format("%s", kursIndonesia.format(resultArr[5])));
                    result8.setText(String.format("%s", kursIndonesia.format(resultArr[6])));
                    result9.setText(String.format("%s", kursIndonesia.format(resultArr[7])));

                    drawMap();

                    JOptionPane.showMessageDialog(null, "Perhitungan sudah selesai. Silahkan klik map bila hasil tidak tampil pada map.");
                    resetFlag = true;
                } catch (StringIndexOutOfBoundsException | NumberFormatException error) {
                    JOptionPane.showMessageDialog(rootPanel, "Input invalid", "Error", ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Input invalid", "Error", ERROR_MESSAGE);
            }
        });

        processButton.addActionListener(e -> {
            if (!result1.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Karyawan kami akan menjemput dan mengantar anda ketempat tujuan.");
                JOptionPane.showMessageDialog(null, "Terima kasih telah menggunakan layanan OwJek.");

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                historyModel.addRow(new Object[]{dateFormat.format(date), username, startField.getText(), finishField.getText(), typeCombobox.getSelectedItem().toString(),
                        result1.getText(), result3.getText(), result4.getText(), result5.getText(), result6.getText(), result7.getText(), result8.getText(), result9.getText()});
            } else {
                JOptionPane.showMessageDialog(null, "Silahkan simulasikan dahulu perjalanan anda.");
            }

        });

        saveHistory.addActionListener(e -> saveHistoryTable());

        userPicture.addComponentListener(new ComponentAdapter() {
        });
        userPicture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                chooseUserPicture();
            }
        });

        saveProfile.addActionListener(e -> {
            user.setInfo(username, phonenumberField.getText(), emailField.getText(), fullnameField.getText(),
                    birthdateField.getText(), birthlocationField.getText(), genderField.getSelectedItem().toString(),
                    statusField.getSelectedItem().toString(), occupationField.getText(), addressField.getText()
            );

            emailField.setEditable(false);
            fullnameField.setEditable(false);
            phonenumberField.setEditable(false);
            birthdateField.setEditable(false);
            birthlocationField.setEditable(false);
            genderField.setEditable(false);
            statusField.setEditable(false);
            occupationField.setEditable(false);
            addressField.setEditable(false);
            genderField.setEnabled(false);
            statusField.setEnabled(false);

            JOptionPane.showMessageDialog(null, "Profil berhasi disimpan.");
        });

        editProfile.addActionListener(e -> {
            emailField.setEditable(true);
            fullnameField.setEditable(true);
            phonenumberField.setEditable(true);
            birthdateField.setEditable(true);
            birthlocationField.setEditable(true);
            genderField.setEditable(true);
            statusField.setEditable(true);
            occupationField.setEditable(true);
            addressField.setEditable(true);
            genderField.setEnabled(true);
            statusField.setEnabled(true);
        });
    }

    /**
     * Menggambar map pada main frame.
     */
    public void drawMap() {
        main.removeAll();

        Dimension rectDimension = new Dimension(10, 10);
        for (int i = 0; i < owjekEngine.getMap().getHEIGHT(); i++) {
            for (int j = 0; j < owjekEngine.getMap().getWIDTH(); j++) {
                Point point = new Point(j, i);
                if (owjekEngine.getMap().get(i, j) == '#') {
                    JButton button$ij = new JButton();
                    button$ij.setBackground(Color.blue);
                    button$ij.setBorderPainted(false);
                    button$ij.setFocusPainted(false);
                    button$ij.setPreferredSize(rectDimension);
                    main.add(button$ij);
                } else if (owjekEngine.getMap().get(i, j) == ' ') {
                    JButton button$ij = new JButton();
                    button$ij.setBackground(Color.white);
                    button$ij.setBorderPainted(false);
                    button$ij.setFocusPainted(false);
                    button$ij.setPreferredSize(rectDimension);

                    button$ij.addActionListener(e -> {
                        if (!resetFlag) {
                            String[] alphaY = {"A", "B", "C", "D", "E"};
                            String[] alphaX = {"Q", "R", "S", "T", "U", "F", "W", "X", "Y", "Z"};
                            String[] option = {"", "Start", "Finish"};
                            String koordinatOption = (String) JOptionPane.showInputDialog(null,
                                    "Koordinat apakah ini ?",
                                    "Koordinat Start atau Finish",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    option,
                                    option[0]);
                            int xCoor = (int) point.getX();
                            int yCoor = (int) point.getY();
                            if (koordinatOption.equals("Start")) {
                                System.out.println(xCoor);
                                System.out.println(yCoor);
                                button$ij.setBackground(Color.red);
                                startField.setText("" + alphaY[yCoor / 10] + yCoor % 10 + alphaX[xCoor / 10] + xCoor % 10);
                            } else if (koordinatOption.equals("Finish")) {
                                System.out.println(xCoor);
                                System.out.println(yCoor);
                                button$ij.setBackground(Color.green);
                                finishField.setText("" + alphaY[yCoor / 10] + yCoor % 10 + alphaX[xCoor / 10] + xCoor % 10);
                            } else {
                                JOptionPane.showMessageDialog(null, "Harap pilih start atau finish!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Untuk mensimulasikan perjalanan baru, silahkan reset terlebih dahulu!");
                        }
                    });

                    main.add(button$ij);
                } else if (owjekEngine.getMap().get(i, j) == '.'
                        || owjekEngine.getMap().get(i, j) == 'S'
                        || owjekEngine.getMap().get(i, j) == 'F') {
                    JButton button$ij = new JButton();
                    button$ij.setBorderPainted(false);
                    button$ij.setFocusPainted(false);
                    button$ij.setBackground(Color.lightGray);
                    button$ij.setPreferredSize(rectDimension);

                    if (owjekEngine.getMap().get(i, j) == 'S') {
                        button$ij.setToolTipText("start");
                        button$ij.setBackground(Color.red);
                    }

                    if (owjekEngine.getMap().get(i, j) == 'F') {
                        button$ij.setToolTipText("finish");
                        button$ij.setBackground(Color.green);
                    }

                    main.add(button$ij);
                }
            }
        }
    }

    /**
     * Simpan history table dalam format CSV.
     */
    public void saveHistoryTable() {
        JFileChooser chooser = new JFileChooser();
        int state = chooser.showSaveDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null && state == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                PrintWriter fileWriter = new PrintWriter(bufferedWriter);
                for (int i = 0; i < historyModel.getRowCount(); i++) {
                    for (int j = 0; j < historyModel.getColumnCount(); j++) {
                        Object o = historyModel.getValueAt(i, j);
                        String s = (o == null ? "" : String.format("\"%s\"", o.toString()));
                        System.out.print(s);
                        bufferedWriter.write(s);

                        if (j < historyModel.getColumnCount() - 1) {
                            bufferedWriter.write(",");
                        } else {
                            bufferedWriter.write("\r\n");
                        }
                    }
                }

                fileWriter.close();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal disimpan.");
            }
        }
    }


    public void chooseUserPicture() {
        JFileChooser chooser = new JFileChooser();
        int state = chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();

        try {
            String mimetype = new MimetypesFileTypeMap().getContentType(file);
            String type = mimetype.split("/")[0];

            if (type.equals("image") && state == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "It's an image");
                ImageIcon userIcon = new ImageIcon(file.getAbsolutePath());
                userPicture.setIcon(userIcon);
            } else
                JOptionPane.showMessageDialog(null, "It's NOT an image");
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane = new JTabbedPane();
        rootPanel.add(tabbedPane, BorderLayout.CENTER);
        OwJek = new JPanel();
        OwJek.setLayout(new BorderLayout(0, 0));
        tabbedPane.addTab("OwJek", OwJek);
        main.setBackground(new Color(-5054501));
        main.setForeground(new Color(-5054501));
        OwJek.add(main, BorderLayout.CENTER);
        side = new JPanel();
        side.setLayout(new GridBagLayout());
        side.setBackground(new Color(-5054501));
        OwJek.add(side, BorderLayout.EAST);
        startLabel = new JLabel();
        startLabel.setText("Start");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(startLabel, gbc);
        finishLabel = new JLabel();
        finishLabel.setText("Finish");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(finishLabel, gbc);
        startField = new JTextField();
        startField.setColumns(4);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(startField, gbc);
        finishField = new JTextField();
        finishField.setColumns(4);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(finishField, gbc);
        goButton = new JButton();
        goButton.setText("Go");
        goButton.setToolTipText("simulasi perjalanan");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(goButton, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result1, gbc);
        distanceLabel = new JLabel();
        distanceLabel.setText("Distance");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(distanceLabel, gbc);
        owjektypeLabel = new JLabel();
        owjektypeLabel.setText("OwJek Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(owjektypeLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result2, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 18;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result3, gbc);
        fixedcostLabel = new JLabel();
        fixedcostLabel.setText("Fixed Cost");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(fixedcostLabel, gbc);
        first2kmLabel = new JLabel();
        first2kmLabel.setText("First 2 Km");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 19;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(first2kmLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 20;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result4, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 22;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result5, gbc);
        first5kmLabel = new JLabel();
        first5kmLabel.setText("First 5 Km");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 21;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(first5kmLabel, gbc);
        completekmLabel = new JLabel();
        completekmLabel.setText("Complete Km");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 23;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(completekmLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 24;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result6, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 26;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result7, gbc);
        promoLabel = new JLabel();
        promoLabel.setText("Promo");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 25;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(promoLabel, gbc);
        prtkscostLabel = new JLabel();
        prtkscostLabel.setText("Prtks Cost");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 27;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(prtkscostLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 28;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result8, gbc);
        totalLabel = new JLabel();
        totalLabel.setText("Total");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 29;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(totalLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 30;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(result9, gbc);
        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setToolTipText("reset simulasi");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(resetButton, gbc);
        processButton = new JButton();
        processButton.setText("Process");
        processButton.setToolTipText("order simulasi");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 32;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(processButton, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        side.add(typeCombobox, gbc);
        final JSeparator separator1 = new JSeparator();
        separator1.setBackground(new Color(-5054501));
        separator1.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        side.add(separator1, gbc);
        final JSeparator separator2 = new JSeparator();
        separator2.setBackground(new Color(-5054501));
        separator2.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        side.add(separator2, gbc);
        final JSeparator separator3 = new JSeparator();
        separator3.setBackground(new Color(-5054501));
        separator3.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        side.add(separator3, gbc);
        final JSeparator separator4 = new JSeparator();
        separator4.setBackground(new Color(-5054501));
        separator4.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        side.add(separator4, gbc);
        final JSeparator separator5 = new JSeparator();
        separator5.setBackground(new Color(-5054501));
        separator5.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        side.add(separator5, gbc);
        final JSeparator separator6 = new JSeparator();
        separator6.setBackground(new Color(-5054501));
        separator6.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 31;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        side.add(separator6, gbc);
        usernameLabel = new JLabel();
        usernameLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(usernameLabel, gbc);
        greetLabel = new JLabel();
        greetLabel.setText("Welcome, ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        side.add(greetLabel, gbc);
        sideLogo = new JLabel();
        sideLogo.setIcon(new ImageIcon(getClass().getResource("/img/Asset 2.png")));
        sideLogo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 7;
        side.add(sideLogo, gbc);
        final JSeparator separator7 = new JSeparator();
        separator7.setBackground(new Color(-5054501));
        separator7.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        side.add(separator7, gbc);
        final JSeparator separator8 = new JSeparator();
        separator8.setBackground(new Color(-5054501));
        separator8.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 0, 0, 0);
        side.add(separator8, gbc);
        final JSeparator separator9 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 28;
        gbc.fill = GridBagConstraints.BOTH;
        side.add(separator9, gbc);
        final JSeparator separator10 = new JSeparator();
        separator10.setBackground(new Color(-5054501));
        separator10.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 33;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 100, 0, 0);
        side.add(separator10, gbc);
        final JSeparator separator11 = new JSeparator();
        separator11.setBackground(new Color(-5054501));
        separator11.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.gridheight = 33;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 100);
        side.add(separator11, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        tabbedPane.addTab("Profile", panel1);
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridBagLayout());
        imagePanel.setBackground(new Color(-5054501));
        panel1.add(imagePanel, BorderLayout.WEST);
        userPicture = new JLabel();
        userPicture.setIcon(new ImageIcon(getClass().getResource("/img/default-user.png")));
        userPicture.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        imagePanel.add(userPicture, gbc);
        final JSeparator separator12 = new JSeparator();
        separator12.setBackground(new Color(-5054501));
        separator12.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 200);
        imagePanel.add(separator12, gbc);
        final JSeparator separator13 = new JSeparator();
        separator13.setBackground(new Color(-5054501));
        separator13.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 200);
        imagePanel.add(separator13, gbc);
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(new Color(-5054501));
        panel1.add(infoPanel, BorderLayout.CENTER);
        info1Lbl = new JLabel();
        info1Lbl.setFont(new Font("Consolas", info1Lbl.getFont().getStyle(), info1Lbl.getFont().getSize()));
        info1Lbl.setText("Username   ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info1Lbl, gbc);
        info2Lbl = new JLabel();
        info2Lbl.setFont(new Font("Consolas", info2Lbl.getFont().getStyle(), info2Lbl.getFont().getSize()));
        info2Lbl.setText("Email");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info2Lbl, gbc);
        usernameField.setColumns(20);
        usernameField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(usernameField, gbc);
        emailField = new JTextField();
        emailField.setColumns(20);
        emailField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(emailField, gbc);
        info3Lbl = new JLabel();
        info3Lbl.setFont(new Font("Consolas", info3Lbl.getFont().getStyle(), info3Lbl.getFont().getSize()));
        info3Lbl.setText("Full Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info3Lbl, gbc);
        info4Lbl = new JLabel();
        info4Lbl.setFont(new Font("Consolas", info4Lbl.getFont().getStyle(), info4Lbl.getFont().getSize()));
        info4Lbl.setText("Birth Date");
        info4Lbl.setToolTipText("dd-mm-yyyy");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info4Lbl, gbc);
        info5Lbl = new JLabel();
        info5Lbl.setFont(new Font("Consolas", info5Lbl.getFont().getStyle(), info5Lbl.getFont().getSize()));
        info5Lbl.setText("Birth Location   ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info5Lbl, gbc);
        info6Lbl = new JLabel();
        info6Lbl.setFont(new Font("Consolas", info6Lbl.getFont().getStyle(), info6Lbl.getFont().getSize()));
        info6Lbl.setText("Gender");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info6Lbl, gbc);
        info7Lbl = new JLabel();
        info7Lbl.setFont(new Font("Consolas", info7Lbl.getFont().getStyle(), info7Lbl.getFont().getSize()));
        info7Lbl.setText("Status");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info7Lbl, gbc);
        info8Lbl = new JLabel();
        info8Lbl.setFont(new Font("Consolas", info8Lbl.getFont().getStyle(), info8Lbl.getFont().getSize()));
        info8Lbl.setText("Occupation");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info8Lbl, gbc);
        info9Lbl = new JLabel();
        info9Lbl.setFont(new Font("Consolas", info9Lbl.getFont().getStyle(), info9Lbl.getFont().getSize()));
        info9Lbl.setText("Home Address");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(info9Lbl, gbc);
        fullnameField = new JTextField();
        fullnameField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(fullnameField, gbc);
        birthdateField = new JTextField();
        birthdateField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(birthdateField, gbc);
        birthlocationField = new JTextField();
        birthlocationField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(birthlocationField, gbc);
        occupationField = new JTextField();
        occupationField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 13;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(occupationField, gbc);
        basicLbl = new JLabel();
        basicLbl.setFont(new Font("Courier New", Font.BOLD, 20));
        basicLbl.setText("Basic Info");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(basicLbl, gbc);
        advanceLbl = new JLabel();
        advanceLbl.setFont(new Font("Courier New", Font.BOLD, 20));
        advanceLbl.setText("Advance Info");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(advanceLbl, gbc);
        addressField = new JTextArea();
        addressField.setEditable(true);
        addressField.setRows(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 14;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(addressField, gbc);
        saveProfile = new JButton();
        saveProfile.setText("Save");
        saveProfile.setToolTipText("save profile");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(saveProfile, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(statusField, gbc);
        editProfile = new JButton();
        editProfile.setText("Edit");
        editProfile.setToolTipText("edit profile");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(editProfile, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(genderField, gbc);
        phonenumberLabel = new JLabel();
        phonenumberLabel.setFont(new Font("Consolas", phonenumberLabel.getFont().getStyle(), phonenumberLabel.getFont().getSize()));
        phonenumberLabel.setText("Phone Number");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(phonenumberLabel, gbc);
        phonenumberField = new JTextField();
        phonenumberField.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(phonenumberField, gbc);
        final JSeparator separator14 = new JSeparator();
        separator14.setBackground(new Color(-5054501));
        separator14.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 15;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        infoPanel.add(separator14, gbc);
        final JSeparator separator15 = new JSeparator();
        separator15.setBackground(new Color(-5054501));
        separator15.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        infoPanel.add(separator15, gbc);
        final JSeparator separator16 = new JSeparator();
        separator16.setBackground(new Color(-5054501));
        separator16.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        infoPanel.add(separator16, gbc);
        final JSeparator separator17 = new JSeparator();
        separator17.setBackground(new Color(-5054501));
        separator17.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        infoPanel.add(separator17, gbc);
        final JSeparator separator18 = new JSeparator();
        separator18.setBackground(new Color(-5054501));
        separator18.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);
        infoPanel.add(separator18, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-5054501));
        tabbedPane.addTab("History", panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setBackground(new Color(-5054501));
        panel2.add(panel3, BorderLayout.SOUTH);
        saveHistory = new JButton();
        saveHistory.setText("Save");
        saveHistory.setToolTipText("simpan history dalam bentuk file");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel3.add(saveHistory, gbc);
        final JSeparator separator19 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel3.add(separator19, gbc);
        final JSeparator separator20 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel3.add(separator20, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-5054501));
        panel2.add(scrollPane1, BorderLayout.CENTER);
        historyTable.setBackground(new Color(-5054501));
        scrollPane1.setViewportView(historyTable);
        About = new JPanel();
        About.setLayout(new GridBagLayout());
        About.setBackground(new Color(-8336444));
        About.setForeground(new Color(-8336444));
        About.setName("About");
        tabbedPane.addTab("About", About);
        final JSeparator separator21 = new JSeparator();
        separator21.setBackground(new Color(-8336444));
        separator21.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 15, 0);
        About.add(separator21, gbc);
        final JSeparator separator22 = new JSeparator();
        separator22.setBackground(new Color(-5054501));
        separator22.setForeground(new Color(-5054501));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        About.add(separator22, gbc);
        logoAbout = new JLabel();
        logoAbout.setIcon(new ImageIcon(getClass().getResource("/img/Asset 1.png")));
        logoAbout.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        About.add(logoAbout, gbc);
        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 22));
        label1.setText("Created by  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        About.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setFont(new Font(label2.getFont().getName(), label2.getFont().getStyle(), 20));
        label2.setText("Arga Ghulam Ahmad");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        About.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setFont(new Font(label3.getFont().getName(), label3.getFont().getStyle(), 20));
        label3.setText("1606821601");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        About.add(label3, gbc);
        argaLogo = new JLabel();
        argaLogo.setIcon(new ImageIcon(getClass().getResource("/img/Asset 4.png")));
        argaLogo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        About.add(argaLogo, gbc);
        final JLabel label4 = new JLabel();
        label4.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 22));
        label4.setText("Powered by");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        About.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setFont(new Font("Fira Code", Font.PLAIN, 26));
        label5.setText("Cepat, Ramah, Murah");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        About.add(label5, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
