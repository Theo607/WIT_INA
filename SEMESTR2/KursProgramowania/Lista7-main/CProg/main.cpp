#include <iostream>
#include <iomanip>
using namespace std;

template <typename T>
class TreeNode {
public:
    T data;
    TreeNode* left;
    TreeNode* right;

    TreeNode(T val) : data(val), left(nullptr), right(nullptr) {}
};

template <typename T>
class Tree {
private:
    TreeNode<T>* root;

    TreeNode<T>* insert(TreeNode<T>* node, T val) {
        if (!node) return new TreeNode<T>(val);
        if (val <= node->data) node->left = insert(node->left, val);
        else node->right = insert(node->right, val);
        return node;
    }

    bool search(TreeNode<T>* node, T val) {
        if (!node) return false;
        if (node->data == val) return true;
        if (val < node->data) return search(node->left, val);
        else return search(node->right, val);
    }

    TreeNode<T>* findMin(TreeNode<T>* node) {
        while (node && node->left) node = node->left;
        return node;
    }

    TreeNode<T>* remove(TreeNode<T>* node, T val) {
        if (!node) return nullptr;

        if (val < node->data) {
            node->left = remove(node->left, val);
        } else if (val > node->data) {
            node->right = remove(node->right, val);
        } else {
            // Match found
            if (!node->left) {
                TreeNode<T>* temp = node->right;
                delete node;
                return temp;
            } else if (!node->right) {
                TreeNode<T>* temp = node->left;
                delete node;
                return temp;
            } else {
                TreeNode<T>* temp = findMin(node->right);
                node->data = temp->data;
                node->right = remove(node->right, temp->data);
            }
        }

        return node;
    }

    void draw(TreeNode<T>* node, int space = 0, int indent = 4) {
        if (!node) return;
        space += indent;
        draw(node->right, space);
        cout << setw(space) << node->data << "\n";
        draw(node->left, space);
    }

    void destroy(TreeNode<T>* node) {
        if (!node) return;
        destroy(node->left);
        destroy(node->right);
        delete node;
    }

public:
    Tree() : root(nullptr) {}
    ~Tree() { destroy(root); }

    void insert(T val) { root = insert(root, val); }
    bool search(T val) { return search(root, val); }
    void remove(T val) { root = remove(root, val); }
    void draw() { draw(root); }
};

int main() {
    string selectedType;
    string cmd;
    string value;
    bool exitBool;

    while(true) {
        exitBool = false;
        cout << "Select type (string, int , double): ";
        cin >> selectedType;
        if (selectedType == "string") {
            Tree<string> treeStr;
            while(!exitBool) {
                cout << "Command: ";
                cin >> cmd;

                if (cmd == "exit") {
                    exitBool = true;
                } else if (cmd == "insert") {
                    cout << "Value: ";
                    cin >> value;
                    treeStr.insert(value);
                } else if (cmd == "search") {
                    cout << "Value: ";
                    cin >> value;
                    cout << "Search: " << (treeStr.search(value) ? "true" : "false") << "\n";
                } else if (cmd == "delete") {
                    cout << "Value: ";
                    cin >> value;
                    treeStr.remove(value);
                } else if (cmd == "draw") {
                    treeStr.draw();
                }
            }
        } else if (selectedType == "int") {
            Tree<int> treeInt;
            while(!exitBool) {
                cout << "Command: ";
                cin >> cmd;

                if (cmd == "exit") {
                    exitBool = true;
                } else if (cmd == "insert") {
                    cout << "Value: ";
                    cin >> value;
                    int val = stoi(value);
                    treeInt.insert(val);
                } else if (cmd == "search") {
                    cout << "Value: ";
                    cin >> value;
                    int val = stoi(value);
                    cout << "Search: " << (treeInt.search(val) ? "true" : "false") << "\n";
                } else if (cmd == "delete") {
                    cout << "Value: ";
                    cin >> value;
                    int val = stoi(value);
                    treeInt.remove(val);
                } else if (cmd == "draw") {
                    treeInt.draw();
                }
            }
        } else if (selectedType == "double") {
            Tree<double> treeDouble;
            while(!exitBool) {
                cout << "Command: ";
                cin >> cmd;

                if (cmd == "exit") {
                    exitBool = true;
                } else if (cmd == "insert") {
                    cout << "Value: ";
                    cin >> value;
                    double val = stod(value);
                    treeDouble.insert(val);
                } else if (cmd == "search") {
                    cout << "Value: ";
                    cin >> value;
                    double val = stod(value);
                    cout << "Search: " << (treeDouble.search(val) ? "true" : "false") << "\n";
                } else if (cmd == "delete") {
                    cout << "Value: ";
                    cin >> value;
                    double val = stod(value);
                    treeDouble.remove(val);
                } else if (cmd == "draw") {
                    treeDouble.draw();
                }
            }
        } else if (cmd == "exit") {
            break;
        }
    }
}