section .data
    msg db "Suma cyfr: "
    msg_len equ $ - msg
    newline db 10

section .bss
    buffer resb 32
    result resb 16

section .text
    global _start

_start:
    mov eax, 3
    mov ebx, 0
    mov ecx, buffer
    mov edx, 32
    int 0x80

    mov esi, buffer
    xor eax, eax

sum_loop:
    movzx ebx, byte [esi]
    cmp bl, 10
    je convert
    sub ebx, '0'
    add eax, ebx
    inc esi
    jmp sum_loop

convert:
    mov ebx, 10
    mov ecx, result + 15
    mov byte [ecx], 0

conv_loop:
    dec ecx
    xor edx, edx
    div ebx
    add dl, '0'
    mov [ecx], dl
    test eax, eax
    jnz conv_loop

    mov esi, ecx

    mov eax, 4
    mov ebx, 1
    mov ecx, msg
    mov edx, msg_len
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, esi
    mov edx, result + 15
    sub edx, ecx
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, newline
    mov edx, 1
    int 0x80

    mov eax, 1
    xor ebx, ebx
    int 0x80

