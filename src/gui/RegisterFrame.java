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
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("ע��");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        idLabel = new JLabel("���֤��");
        idLabel.setBounds(90, 80, 70, 30);
        idField = new JTextField();
        idField.setBounds(170, 80, 150, 30);

        nameLabel = new JLabel("����");
        nameLabel.setBounds(90, 130, 70, 30);
        nameField = new JTextField();
        nameField.setBounds(170, 130, 150, 30);

        pswLabel = new JLabel("����");
        pswLabel.setBounds(90, 180, 70, 30);
        pswField = new JPasswordField();
        pswField.setBounds(170, 180, 150, 30);

        confirmLabel = new JLabel("ȷ������");
        confirmLabel.setBounds(90, 230, 70, 30);
        confirmField = new JPasswordField();
        confirmField.setBounds(170, 230, 150, 30);


        registerButton = new JButton("ע��");
        registerButton.setBounds(80, 320, 80, 30);
        registerButton.addActionListener(new RegisterAction());

        returnButton = new JButton("����");
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
     * �ж������Ƿ���Ч
     * @param name ����
     * @return ��ЧΪtrue����ЧΪfalse
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
                        "������������Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!AddPassengerFrame.validID(id))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "��������Ч�����֤�ţ�", "����", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!validName(name))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "��������Ч��������", "����", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (!password.equals(confirmPsw))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                    "������ȷ�����룡", "����", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            if (UserSQL.isUserExisting(id))
            {
                JOptionPane.showMessageDialog(RegisterFrame.this,
                        "���û��Ѵ��ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                initializeInfo();
                return;
            }
            UserSQL.userRegister(id, name, password);
            JOptionPane.showMessageDialog(RegisterFrame.this, "ע��ɹ������¼!");
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
