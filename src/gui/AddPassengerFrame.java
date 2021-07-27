package gui;

import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPassengerFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 300;
    private String userId;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JTextField nameField;
    private JTextField idField;
    private JButton addButton;
    private JButton returnButton;
    private int status = 0;     // 如果为0则返回用户界面，1则返回管理员界面

    public AddPassengerFrame(String userId, int status)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("添加乘车人");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.status = status;
        this.userId = userId;

        nameLabel = new JLabel("姓名");
        nameLabel.setBounds(80, 70, 80, 30);
        idLabel = new JLabel("身份证号");
        idLabel.setBounds(80, 120, 80, 30);
        nameField = new JTextField(10);
        nameField.setBounds(160, 70, 150, 30);
        idField = new JTextField(10);
        idField.setBounds(160, 120, 150, 30);
        addButton = new JButton("添加");
        addButton.setBounds(100, 200, 80, 30);
        addButton.addActionListener(new AddAction());
        returnButton = new JButton("返回");
        returnButton.addActionListener(new ReturnAction());
        returnButton.setBounds(220, 200, 80, 30);

        add(nameLabel);
        add(idLabel);
        add(nameField);
        add(idField);
        add(addButton);
        add(returnButton);

        setVisible(true);
    }

    public JTextField getNameField()
    {
        return nameField;
    }

    public JTextField getIdField()
    {
        return idField;
    }

    /**
     * 判断身份证号码是否有效
     * @param id 身份证号码
     * @return 有效返回true，否则为false
     */
    public static boolean validID(String id)
    {
        boolean valid = true;
        if (id.length() != 18)
            return false;
        for (int i = 0; i < 17; i++)
            if (!Character.isDigit(id.charAt(i)))
            {
                return false;
            }
        if (!(Character.isDigit(id.charAt(17)) || id.charAt(17) == 'x' || id.charAt(17) == 'X'))
            valid = false;
        return valid;
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String id = AddPassengerFrame.this.getIdField().getText();
            String name = AddPassengerFrame.this.getNameField().getText();
            if (name.equals("") || id.equals(""))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "请输入乘车人信息！");
                return;
            }
            if (!validID(id))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "请输入正确的身份证号！");
                return;
            }
            if (!RegisterFrame.validName(name))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this,
                        "请输入有效的姓名！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (UserSQL.isAddPassExisting(userId, id))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "该乘车人已存在！");
                return;
            }
            UserSQL.insertAddPassenger(userId, id);
            if (UserSQL.isPassExisting(id));
            else
                UserSQL.insertPassenger(id, name);
            JOptionPane.showMessageDialog(AddPassengerFrame.this, "添加成功!");
            dispose();
            if (status == 0)
                new PassengerFrame(userId);
            else if (status == 1)
                new ModifyUserFrame(userId);
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            if (status == 0)
                new PassengerFrame(userId);
            else if (status == 1)
                new ModifyUserFrame(userId);
        }
    }
}
