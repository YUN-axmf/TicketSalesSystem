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
    private int status = 0;     // ���Ϊ0�򷵻��û����棬1�򷵻ع���Ա����

    public AddPassengerFrame(String userId, int status)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("��ӳ˳���");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.status = status;
        this.userId = userId;

        nameLabel = new JLabel("����");
        nameLabel.setBounds(80, 70, 80, 30);
        idLabel = new JLabel("���֤��");
        idLabel.setBounds(80, 120, 80, 30);
        nameField = new JTextField(10);
        nameField.setBounds(160, 70, 150, 30);
        idField = new JTextField(10);
        idField.setBounds(160, 120, 150, 30);
        addButton = new JButton("���");
        addButton.setBounds(100, 200, 80, 30);
        addButton.addActionListener(new AddAction());
        returnButton = new JButton("����");
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
     * �ж����֤�����Ƿ���Ч
     * @param id ���֤����
     * @return ��Ч����true������Ϊfalse
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
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "������˳�����Ϣ��");
                return;
            }
            if (!validID(id))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "��������ȷ�����֤�ţ�");
                return;
            }
            if (!RegisterFrame.validName(name))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this,
                        "��������Ч��������", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (UserSQL.isAddPassExisting(userId, id))
            {
                JOptionPane.showMessageDialog(AddPassengerFrame.this, "�ó˳����Ѵ��ڣ�");
                return;
            }
            UserSQL.insertAddPassenger(userId, id);
            if (UserSQL.isPassExisting(id));
            else
                UserSQL.insertPassenger(id, name);
            JOptionPane.showMessageDialog(AddPassengerFrame.this, "��ӳɹ�!");
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
