import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {
    JTextField text;
    static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream ds;
    static JFrame f = new JFrame();


    Client(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(10,90,80));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30,30 ,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(10,20,30,30);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/pngwing2.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(50 ,12 ,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300 ,20 ,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/call.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(350 ,20 ,30,30);
        p1.add(call);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/menu.png"));
        Image i14 = i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel menu = new JLabel(i15);
        menu.setBounds(400 ,20 ,30,30);
        p1.add(menu);

        JLabel name = new JLabel("Rolex");
        name.setBounds(110,25,80,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,50,80,10);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,8));
        p1.add(status);

        p2 = new JPanel();
        p2.setBounds(5,75,440,570);
        p2.setLayout(null);
        f.add(p2);

        text = new JTextField();
        text.setBounds(5,650,340,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(350,650,80,40);
        send.setBackground(new Color(10,90,80));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("TEXT",Font.PLAIN,14));
        send.addActionListener(this::actionPerformed);
        f.add(send);

        f.setSize(450,700);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.lightGray);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();

            JPanel p3 = formatLabel(out);

            p2.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p2.add(vertical, BorderLayout.PAGE_START);

            ds.writeUTF(out);

            text.setText("");

            f.validate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma",Font.PLAIN,14));
        output.setBackground(new Color(40,220,100));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel1.add(output);

        Calendar cal =  Calendar.getInstance();
        SimpleDateFormat frm = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(frm.format(cal.getTime()));
        panel1.add(time);

        return  panel1;
    }

    public static void main(String[] args) {
        new Client();
        try {
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream di = new DataInputStream(s.getInputStream());
            ds = new DataOutputStream(s.getOutputStream());

            while (true){
                p2.setLayout(new BorderLayout());
                String msg = di.readUTF();
                JPanel panel1 = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel1,BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                p2.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}