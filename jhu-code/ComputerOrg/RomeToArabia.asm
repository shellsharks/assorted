# Michael Sass - Computer Organization/Module 8 - From Rome to Arabia

.data
	welcome:	.asciiz "From Rome to Arabia!\n\nPlease enter a roman numeral (all upper case): "
	buffer:		.space 20
	numerals:	.asciiz "IVXLCDM"
	outputa:	.asciiz "Your input "
	outputb:	.asciiz "...is equal to "
	final:		.asciiz "\n\nThe application has finished execution."
	
.text

	.globl main
	
main:
		li $v0, 4				# loads syscall code for print_string
		la $a0, welcome			# loads initial welcome message string
		syscall					# prints initial welcome message string 
	
		li $v0, 8				# accepts String input from user for the value to be calculated
		la $a0, buffer			# load byte space into address
		li $a1, 20				# allot the byte space for the string
		move $t0, $a0			# save user input string to t0
		syscall
		
		la $t3, buffer			# put buffer into t3
		move $t3, $t0			# move user input into t3
		li $t1, 0				# initialize counter to 0
		add $t6, $zero, $zero	# initialize final sum value to 0
	
		la $t5, numerals		# loads the "numerals" string into t5
		lb $s0, ($t5)			# sets s0 = "I"
		lb $s1, 1($t5)			# sets s1 = "V"
		lb $s2, 2($t5)			# sets s2 = "X"
		lb $s3, 3($t5)			# sets s3 = "L"
		lb $s4, 4($t5)			# sets s4 = "C"
		lb $s5, 5($t5)			# sets s5 = "D"
		lb $s6, 6($t5)			# sets s6 = "M"

##########################################################################
# This procedure iterates through each character in the user input.
# For each character, it calls the "equals" procedure to calcuate the sum.
# Once a null character is encountered (end of input), the application
# jumps to the final output processing and exits.

iterate:
		lb $t2, 0($t3)			# load current character into t2
		beqz $t2, end			# if t2 == zero (no more characters) then end program
	
		lb $t4, 1($t3)			# load "next" character into t4
	
		jal equals				# calls equals function to identify numeral and add value
		
		################################
		# Code below is used for testing
		
		#move $a0, $t2			# move current character into a0
		#li $v0, 11				# print current character
		#syscall
	
		#addi $a0, $zero, 0xA	# print newline
		#addi $v0, $zero, 0xB
		#syscall
		#################################
		
		add $t3, $t3, 1			# iterate to next character
		add $t1, $t1, 1			# iterate counter
		j iterate
		
####################################################################################
# Logic for comparing user input and adding the final sum is done in this procedure.
# This procedure takes... 
# $t2 as input for the "current character" and
# $t4 as input for the "next character"

equals:
		sw $ra, 4($sp)			# save the return address to the stackpointer
		beq $t2, $s0, I			# if current value is equal to "I", jump to I
		beq $t2, $s1, V			# if current value is equal to "V", jump to V
		beq $t2, $s2, X			# if current value is equal to "X", jump to X
		beq $t2, $s3, L			# if current value is equal to "L", jump to L
		beq $t2, $s4, C			# if current value is equal to "C", jump to C
		beq $t2, $s5, D			# if current value is equal to "D", jump to D
		beq $t2, $s6, M			# if current value is equal to "M", jump to M
		j back
I:		beq $t4, $s1, minI		# if the next roman numeral is a "V", subtract 1 instead of adding
		beq $t4, $s2, minI		# if the next roman numeral is an "X", subtract 1 instead of adding
		beq $t4, $s3, minI		# if the next roman numeral is an "L", subtract 1 instead of adding
		beq $t4, $s4, minI		# if the next roman numeral is an "C", subtract 1 instead of adding
		beq $t4, $s5, minI		# if the next roman numeral is an "D", subtract 1 instead of adding
		beq $t4, $s6, minI		# if the next roman numeral is an "M", subtract 1 instead of adding
		addi $t6, $t6, 1		# add 1 to the final value
		j back
minI:	addi $t6, $t6, -1		# subtract 1 from the final value
		j back
V:		beq $t4, $s3, minV		# if the next roman numeral is an "L", subtract 5 instead of adding
		beq $t4, $s4, minV		# if the next roman numeral is an "C", subtract 5 instead of adding
		beq $t4, $s5, minV		# if the next roman numeral is an "D", subtract 5 instead of adding
		beq $t4, $s6, minV		# if the next roman numeral is an "M", subtract 5 instead of adding
		addi $t6, $t6, 5		# add 5 to the final value
		j back
minV:	addi $t6, $t6, -5		# subtract 5 from the final value
		j back
X:		beq $t4, $s3, minX		# if the next roman numeral is an "L", subtract 10 instead of adding
		beq $t4, $s4, minX		# if the next roman numeral is an "C", subtract 10 instead of adding
		beq $t4, $s5, minX		# if the next roman numeral is an "D", subtract 10 instead of adding
		beq $t4, $s6, minX		# if the next roman numeral is an "M", subtract 10 instead of adding
		addi $t6, $t6, 10		# add 10 to the final value
		j back
minX: 	addi $t6, $t6, -10		# subtract 10 from the final value
		j back
L:		beq $t4, $s5, minL		# if the next roman numeral is an "D", subtract 50 instead of adding
		beq $t4, $s6, minL		# if the next roman numeral is an "M", subtract 50 instead of adding
		addi $t6, $t6, 50		# add 50 to the final value
		j back
minL:	addi $t6, $t6, -50		# subtract 50 from the final value
		j back
C:		beq $t4, $s5, minC		# if the next roman numeral is a "D", subtract 100 instead of adding
		beq $t4, $s6, minC		# if the next roman numeral is an "M", subtract 100 instead of adding
		addi $t6, $t6, 100		# add 100 to the final value
		j back
minC:	addi $t6, $t6, -100		# subtract 100 from the final value
		j back
D:		addi $t6, $t6, 500		# add 500 to the final value
		j back
M:		addi $t6, $t6, 1000		# add 1000 to the final value
		j back
back:
		lw $ra, 4($sp)			# loads return address from stack pointer
		jr $ra					# returns to the calling function
	
######################################
# Final processing/output is done here

end:

	addi $a0, $zero, 0xA		# prints newline
	addi $v0, $zero, 0xB
	syscall

	li $v0, 4					# prints first part of output
	la $a0, outputa
	syscall
	
	li $v0, 4					# prints user input
	move $a0, $t0
	syscall
	
	li $v0, 4					# prints second part of output
	la $a0, outputb
	syscall

	li $v0, 1					# prints final calculated integer value
	move $a0, $t6
	syscall
	
	li $v0, 4					# prints closing message
	la $a0, final
	syscall

	li $v0, 10					# ends program
	syscall