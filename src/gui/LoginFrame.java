package gui;

import dao.LoginSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面类
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
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("车票实名销售系统系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField(10);
        passwordField = new JPasswordField(10);
        textField.setBounds(220,90,250,30);
        passwordField.setBounds(220,170,250,30);

        userLabel = new JLabel("用户名", SwingConstants.RIGHT);
        passwordLabel = new JLabel("密    码", SwingConstants.RIGHT);
        userLabel.setBounds(80, 80, 100, 50);
        passwordLabel.setBounds(80, 160, 100, 50);

        loginButton = new JButton("登录");
        exitButton = new JButton("退出");
        registerButton = new JButton("注册");

        loginButton.setBounds(140, 280, 80, 30);
        registerButton.setBounds(260, 280, 80, 30);
        exitButton.setBounds(380, 280, 80, 30);

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();
        radioButtonPanel.setBounds(200, 220, 200, 50);

        adminRadioButton = new JRadioButton("管理员");
        group.add(adminRadioButton);
        radioButtonPanel.add(adminRadioButton);

        userRadioButton = new JRadioButton("用户");
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
                    JOptionPane.showMessageDialog(LoginFrame.this, "登录成功!");
                    new AdminFrame();
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码输入错误!");
            }
            else if (LoginFrame.this.getUserRadioButton().isSelected())
            {
                if (LoginSQL.verify(id, password, "user"))
                {
                    JOptionPane.showMessageDialog(LoginFrame.this, "登录成功!");
                    new UserFrame(id);
                    dispose();
                }
                else
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码输入错误!");
            }
            else
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "请选择登录身份！", "错误", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(LoginFrame.this, "感谢您的使用！", "", JOptionPane.INFORMATION_MESSAGE );
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
