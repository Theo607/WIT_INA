section .data
    number dd 123456        
    hex_chars db "0123456789abcdef"
    newline db 10

section .bss
    outbuf resb 9           

section .text
    global _start

_start:
    mov eax, [number]       
    mov ecx, outbuf+8   
    mov byte [ecx], 0      

    mov ebx, 16             

hex_loop:
    dec ecx
    xor edx, edx
    div ebx                  
    mov dl, [hex_chars + edx]
    mov [ecx], dl
    test eax, eax
    jnz hex_loop

    
    mov edx, outbuf+8
    sub edx, ecx             
    mov eax, 4               
    mov ebx, 1               
    int 0x80

    
    mov eax, 4
    mov ebx, 1
    mov ecx, newline
    mov edx, 1
    int 0x80

    
    mov eax, 1
    xor ebx, ebx
    int 0x80

