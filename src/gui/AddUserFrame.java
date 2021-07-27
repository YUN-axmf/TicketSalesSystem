package gui;

import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AddUserFrame();
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 360;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JTextField nameField;
    private JTextField idField;
    private JTextField passwordField;
    private JButton addButton;
    private JButton returnButton;

    public AddUserFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("添加用户");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        idLabel = new JLabel("身份证号");
        idLabel.setBounds(80, 70, 80, 30);
        nameLabel = new JLabel("姓名");
        nameLabel.setBounds(80, 120, 80, 30);
        passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(80, 170, 80, 30);
        idField = new JTextField();
        idField.setBounds(160, 70, 150, 30);
        nameField = new JTextField();
        nameField.setBounds(160, 120, 150, 30);
        passwordField = new JTextField();
        passwordField.setBounds(160, 170, 150, 30);
        addButton = new JButton("增加");
        addButton.setBounds(100, 250, 80, 30);
        addButton.addActionListener(new AddAction());
        returnButton = new JButton("返回");
        returnButton.addActionListener(new ReturnAction());
        returnButton.setBounds(220, 250, 80, 30);

        add(nameLabel);
        add(idLabel);
        add(passwordLabel);
        add(nameField);
        add(idField);
        add(passwordField);
        add(addButton);
        add(returnButton);

        setVisible(true);
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String userId = idField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            if (userId.equals("") || name.equals("") || password.equals(""))
            {
                JOptionPane.showMessageDialog(AddUserFrame.this,
                        "请完整输入信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!AddPassengerFrame.validID(userId))
            {
                JOptionPane.showMessageDialog(AddUserFrame.this,
                        "请输入有效的身份证号！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!RegisterFrame.validName(name))
            {
                JOptionPane.showMessageDialog(AddUserFrame.this,
                        "请输入有效的姓名！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (UserSQL.isUserExisting(userId))
            {
                JOptionPane.showMessageDialog(AddUserFrame.this,
                        "该用户已存在！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.userRegister(userId, name, password);
            JOptionPane.showMessageDialog(AddUserFrame.this, "添加用户成功!");
            dispose();
            new AdmUserFrame();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmUserFrame();
        }
    }
}
