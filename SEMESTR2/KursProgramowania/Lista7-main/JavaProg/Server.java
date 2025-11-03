import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public void caseString(BufferedReader in, PrintWriter out) {
        Tree<String> tree = new Tree<>();
        boolean exitFlag = false;
        String cmd = null;
        try {
            while (exitFlag == false) {
                out.println("Command: ");
                cmd = in.readLine();
                if ("insert".equals(cmd)) {
                    out.println("Value: ");
                    String val = in.readLine();
                    tree.insert(val);
                    tree.draw(out);
                } else if ("search".equals(cmd)) {
                    out.println("Value: ");
                    String val = in.readLine();
                    out.println(tree.search(val));
                    out.println("END_DRAW");

                } else if ("delete".equals(cmd)) {
                    out.println("Value: ");
                    String val = in.readLine();
                    tree.delete(val);
                    tree.draw(out);

                } else if ("draw".equals(cmd)) {
                    tree.draw(out);
                }
                if (cmd.equals("exit")) {
                    exitFlag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caseInteger(BufferedReader in, PrintWriter out) {
        Tree<Integer> tree = new Tree<>();
        boolean exitFlag = false;
        String cmd = null;
        try {
            while (exitFlag == false) {
                out.println("Command: ");
                cmd = in.readLine();
                if ("insert".equals(cmd)) {
                    out.println("Value: ");
                    int val = Integer.parseInt(in.readLine());
                    tree.insert(val);
                    tree.draw(out);
                } else if ("search".equals(cmd)) {
                    out.println("Value: ");
                    int val = Integer.parseInt(in.readLine());
                    out.println(tree.search(val));
                    out.println("END_DRAW");

                } else if ("delete".equals(cmd)) {
                    out.println("Value: ");
                    int val = Integer.parseInt(in.readLine());
                    tree.delete(val);
                    tree.draw(out);

                } else if ("draw".equals(cmd)) {
                    tree.draw(out);
                }
                if (cmd.equals("exit")) {
                    exitFlag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void caseDouble(BufferedReader in, PrintWriter out) {
        Tree<Double> tree = new Tree<>();
        boolean exitFlag = false;
        String cmd = null;
        try {
            while (exitFlag == false) {
                out.println("Command: ");
                cmd = in.readLine();
                if ("insert".equals(cmd)) {
                    out.println("Value: ");
                    double val = Double.parseDouble(in.readLine());
                    tree.insert(val);
                    tree.draw(out);
                } else if ("search".equals(cmd)) {
                    out.println("Value: ");
                    double val = Double.parseDouble(in.readLine());
                    out.println(tree.search(val));
                    
                    out.println("END_DRAW");

                } else if ("delete".equals(cmd)) {
                    out.println("Value: ");
                    double val = Double.parseDouble(in.readLine());
                    tree.delete(val);
                    tree.draw(out);

                } else if ("draw".equals(cmd)) {
                    tree.draw(out);
                }
                if (cmd.equals("exit")) {
                    exitFlag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            int port = 1234;
            ServerSocket server = new ServerSocket(port);

            System.out.println("Server started on port: " + port);
            System.out.println("Basic commands info:");
            System.out.println(
                    "- insert <value>\n" + "- search <value>\n" + "- draw\n" + "- delete <value>\n" + "- exit");
            Socket client = server.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            boolean setType = false;

            String typeStr = null;
            boolean shut = false;
            while (!shut) {
                while (setType == false) {
                    out.println("Enter type (string, double, integer) --_shut_ to end--: ");
                    typeStr = in.readLine();
                    if (typeStr.equals("string") || typeStr.equals("double") || typeStr.equals("integer")) {
                        setType = true;
                    }
                    if(typeStr.equals("shut")) {
                        shut = true;
                        break;
                    }
                    switch (typeStr) {
                        case "string":
                            caseString(in, out);
                            setType = false;
                            break;
                        case "integer":
                            caseInteger(in, out);
                            setType = false;
                            break;
                        case "double":
                            caseDouble(in, out);
                            setType = false;
                            break;
                        default:
                            break;
                    }
                }
            }

            client.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
