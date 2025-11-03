// boot.c

void main() {
    char *video_memory = (char*) 0xB8000;
    char *message = "Hello, World!";

    int row = 0;

    for (unsigned char bg = 0; bg < 8; bg++) {
        for (unsigned char fg = 0; fg < 16; fg++) {
            unsigned char color = (bg << 4) | fg;
            for (int i = 0; message[i] != '\0'; i++) {
                int offset = (row * 80 + i) * 2; // 80 columns per row
                video_memory[offset] = message[i];
                video_memory[offset + 1] = color;
            }
            row++;
        }
    }

    while(1); // halt
}
