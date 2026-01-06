section .data
    matrix dd 1,2,3,4,5,6,7,8,9

    msg1 db "Suma elementow: "
    msg1_len equ $ - msg1

    msg2 db "Suma przekatnej: "
    msg2_len equ $ - msg2

    newline db 10

section .bss
    outbuf resb 16

section .text
    global _start

_start:
    mov esi, matrix
    xor eax, eax        
    xor edi, edi       
    xor ecx, ecx      

sum_loop:
    mov edx, [esi]
    add eax, edx

    cmp ecx, 0
    je diag
    cmp ecx, 4
    je diag
    cmp ecx, 8
    je diag
    jmp next

diag:
    add edi, edx

next:
    add esi, 4
    inc ecx
    cmp ecx, 9
    jl sum_loop

    mov esi, eax        

    
    mov eax, 4
    mov ebx, 1
    mov ecx, msg1
    mov edx, msg1_len
    int 0x80

    mov eax, esi
    call print_number
    call print_newline


    mov eax, 4
    mov ebx, 1
    mov ecx, msg2
    mov edx, msg2_len
    int 0x80

    mov eax, edi
    call print_number
    call print_newline

    mov eax, 1
    xor ebx, ebx
    int 0x80



print_number:
    mov esi, outbuf + 15
    mov byte [esi], 0
    mov ebx, 10

.conv:
    dec esi
    xor edx, edx
    div ebx
    add dl, '0'
    mov [esi], dl
    test eax, eax
    jnz .conv

    mov eax, 4
    mov ebx, 1
    mov ecx, esi
    mov edx, outbuf + 15
    sub edx, ecx
    int 0x80
    ret

print_newline:
    mov eax, 4
    mov ebx, 1
    mov ecx, newline
    mov edx, 1
    int 0x80
    ret

