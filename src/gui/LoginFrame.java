package gui;

import dao.LoginSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ��¼������
 */
public class LoginFrame extends JFrame
{
    public static void main(String[] args)
    {
        new LoginFrame();
    }

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton exitButton;
    private JButton registerButton;
    private JPanel radioButtonPanel;
    private ButtonGroup group;
    private JRadioButton userRadioButton;
    private JRadioButton adminRadioButton;

    public LoginFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("��Ʊʵ������ϵͳϵͳ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField(10);
        passwordField = new JPasswordField(10);
        textField.setBounds(220,90,250,30);
        passwordField.setBounds(220,170,250,30);

        userLabel = new JLabel("�û���", SwingConstants.RIGHT);
        passwordLabel = new JLabel("��    ��", SwingConstants.RIGHT);
        userLabel.setBounds(80, 80, 100, 50);
        passwordLabel.setBounds(80, 160, 100, 50);

        loginButton = new JButton("��¼");
        exitButton = new JButton("�˳�");
        registerButton = new JButton("ע��");

        loginButton.setBounds(140, 280, 80, 30);
        registerButton.setBounds(260, 280, 80, 30);
        exitButton.setBounds(380, 280, 80, 30);

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();
        radioButtonPanel.setBounds(200, 220, 200, 50);

        adminRadioButton = new JRadioButton("����Ա");
        group.add(adminRadioButton);
        radioButtonPanel.add(adminRadioButton);

        userRadioButton = new JRadioButton("�û�");
        group.add(userRadioButton);
        radioButtonPanel.add(userRadioButton);

        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(new RegisterAction());
        exitButton.addActionListener(new ExitAction());

        add(userLabel);
        add(passwordLabel);
        add(textField);
        add(passwordField);
        add(loginButton);
        add(registerButton);
        add(exitButton);
        add(radioButtonPanel);

        setVisible(true);
    }

    private class LoginAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String id = LoginFrame.this.getTextField().getText();
            String password = LoginFrame.this.getPasswordField().getText();
            if (LoginFrame.this.getAdminRadioButton().isSelected())
            {
                if (LoginSQL.verify(id, password, "admin"))
                {
                    JOptionPane.showMessageDialog(LoginFrame.this, "��¼�ɹ�!");
                    new AdminFrame();
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(LoginFrame.this, "�û����������������!");
            }
            else if (LoginFrame.this.getUserRadioButton().isSelected())
            {
                if (LoginSQL.verify(id, password, "user"))
                {
                    JOptionPane.showMessageDialog(LoginFrame.this, "��¼�ɹ�!");
                    new UserFrame(id);
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(LoginFrame.this, "�û����������������!");
            }
            else
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "��ѡ���¼��ݣ�", "����", JOptionPane.WARNING_MESSAGE);
        }
    }

    private class RegisterAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new RegisterFrame();
            dispose();
        }
    }

    private class ExitAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JOptionPane.showMessageDialog(LoginFrame.this, "��л����ʹ�ã�", "", JOptionPane.INFORMATION_MESSAGE );
            System.exit(0);
        }
    }

    public JRadioButton getUserRadioButton()
    {
        return userRadioButton;
    }

    public JRadioButton getAdminRadioButton()
    {
        return adminRadioButton;
    }

    public JTextField getTextField()
    {
        return textField;
    }

    public JPasswordField getPasswordField()
    {
        return passwordField;
    }
}
