package gui;

import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame
{
    public static void main(String[] args)
    {
        new RegisterFrame();
    }
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel pswLabel;
    private JLabel confirmLabel;
    private JTextField idField;
    private JTextField nameField;
    private JPasswordField pswField;
    private JPasswordField confirmField;
    private JButton registerButton;
    private JButton returnButton;

    public RegisterFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("注册");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        idLabel = new JLabel("身份证号");
        idLabel.setBounds(90, 80, 70, 30);
        idField = new JTextField();
        idField.setBounds(170, 80, 150, 30);

        nameLabel = new JLabel("姓名");
        nameLabel.setBounds(90, 130, 70, 30);
        nameField = new JTextField();
        nameField.setBounds(170, 130, 150, 30);

        pswLabel = new JLabel("密码");
        pswLabel.setBounds(90, 180, 70, 30);
        pswField = new JPasswordField();
        pswField.setBounds(170, 180, 150, 30);

        confirmLabel = new JLabel("确认密码");
        confirmLabel.setBounds(90, 230, 70, 30);
        confirmField = new JPasswordField();
        confirmField.setBounds(170, 230, 150, 30);


        registerButton = new JButton("注册");
        registerButton.setBounds(80, 320, 80, 30);
        registerButton.addActionListener(new RegisterAction());

        returnButton = new JButton("返回");
        returnButton.setBounds(240, 320, 80, 30);
        returnButton.addActionListener(new ReturnAction());

        add(idLabel);
        add(nameLabel);
        add(pswLabel);
        add(confirmLabel);
        add(idField);
        add(nameField);
        add(pswField);
        add(confirmField);
        add(registerButton);
        add(returnButton);

        setVisible(true);
    }

    /**
     * 判断姓名是否有效
     * @param name 姓名
     * @return 有效为true，无效为false
     */
    public static boolean validName(String name)
    {
        boolean valid = true;
        if (name.length() <= 1 || name.length() >= 7)
            valid = false;
        for (int i = 0; i < name.length(); i++)
            if (!String.valueOf(name.charAt(i)).matches("[\u4e00-\u9fa5]"))
            {
                valid = false;
                break;
            }
        return valid;
    }

    public void initializeInfo()
    {
        idField.setText("");
        nameField.setText("");
        pswField.setText("");
        confirmField.setText("");
    }

    private class RegisterAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String id = idField.getText();
            String name = nameField.getText();
            String password = pswField.getText();
            String confirmPsw = confirmField.getText();
            if (id.equals("") || name.equals("") || password.equals("") || confirmPsw.equals(""))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "请完整输入信息！", "错误", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!AddPassengerFrame.validID(id))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "请输入有效的身份证号！", "错误", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!validName(name))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "请输入有效的姓名！", "错误", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!password.equals(confirmPsw))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                    "请重新确认密码！", "错误", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (UserSQL.isUserExisting(id))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "该用户已存在！", "错误", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            UserSQL.userRegister(id, name, password);
            JOptionPane.showMessageDialog(RegisterFrame.this, "注册成功，请登录!");
            dispose();
            new LoginFrame();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new LoginFrame();
        }
    }
}
