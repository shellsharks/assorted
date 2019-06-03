# Michael Sass - Module 7 - Lottery Jackpot Odds

.data
	largepool:		.asciiz "How many numbers are in the large pool?: "
	largecount:		.asciiz "How many numbers are being selected from the large pool?: "
	smallpool:		.asciiz "How many numbers are in the small pool?: "
	smallcount:		.asciiz "How many numbers are being selected from the small pool?: "
	
	message:		.asciiz "\nThe odds are 1 in "
	complete:		.asciiz "\nThe program has completed."
	
.text

	.globl	main

main:
	#Prompt user to enter large pool number.
	li $v0, 4				# loads syscall code for print_string
	la $a0, largepool		# loads largepool string
	syscall					# prints largepool string
	
	li $v0, 5				# accepts integer input from user for large pool number
	syscall					# system call
	
	move $s0, $v0			# store inputted integer in $s0
	
	#Prompt user to enter count from large pool.
	li $v0, 4				# loads syscall code for print_string
	la $a0, largecount		# loads largecount string
	syscall					# prints largecount string
	
	li $v0, 5				# accepts integer input from user for large count number
	syscall					# system call
	
	move $s1, $v0			# store inputted integer in $s1
	
	#Prompt user to enter small pool number.
	li $v0, 4				# loads syscall code for print_string
	la $a0, smallpool		# loads smallpool string
	syscall					# prints smallpool string
	
	li $v0, 5				# accepts integer input from user for small pool number
	syscall					# system call

	move $s2, $v0			# store inputted integer in $s2
	
	#Prompt user to enter count from small pool.
	li $v0, 4				# loads syscall code for print_string
	la $a0, smallcount		# loads smallcount string
	syscall					# prints smallcount string
	
	li $v0, 5				# accepts integer input from user for small count number
	syscall					# system call
	
	move $s3, $v0			# store inputted integer in $s3
	
##############################################	
# Main procedure calls and final lotto odds calculated here

	or $a0, $s0, $zero		# store largepool number in $a0
	or $a1, $s1, $zero		# store largepool count in $a1
	jal lotto				# run lotto procedure with largepool
	move $s0, $v0			# take output of lotto procedure and store in $s0
	or $a0, $s2, $zero		# store smallpool number in $a0
	or $a1, $s3, $zero		# store smallpool count in $a1
	jal lotto				# run lotto procedure with smallpool numbers
	mul $s0, $s0, $v0		# set $s0 to the product of both lotto procedures
	
##############################################
# Display the odds message

	li $v0, 4				# load syscall code for print_string
	la $a0, message			# loads message string
	syscall					# prints message string
	
	li $v0, 1				# loads syscall code for print_int
	move $a0, $s0			# accepts input as integer
	syscall					# accepts input as integer
	
	li $v0, 4				# loads syscall code for print_string
	la $a0, complete		# loads complete string
	syscall					# prints complete string
	
	li $v0, 10				# loads syscall code for exit
	syscall					# exits application
	
##############################################
# Given a pool of numbers "n", stored in $a0
# and a count of numbers "k" from that pool, stored in $a1
# calculates the denominator, stores and returns the result in register $v0 

lotto:	or $t5, $ra, $zero	# save the return address
		sub $t0, $a0, $a1	# calculate n-k
		sub $t0, $a0, $t0	# calculates n-(n-k)
		addi $t1, $zero, 1	# sets a counter $t1 to 1
		or $v1, $a0, $zero	# sets sum to n
		
calc:	beq $t1, $t0, exit	# while $t1 != n-(n-k)
		sub $t2, $a0, $t1	# nextval = n - counter
		mul $v1, $v1, $t2	# sum = sum * (n-counter)
		addi $t1, $t1, 1	# counter++
		jal calc			# now nextval

exit:	or $a0, $a1, $zero	# sets $a0 = k
		jal factrl			# calls factorial procedure to calculate k!
		or $ra, $t5, $zero	# fetch return address
		div $v0, $v1, $v0 	# calculate n!/k!(n-k)!
		jr $ra				# return pool odds
	
##############################################
# Given n, in register $a0
# calculate n!, store and return the result in register $v0

factrl:	sw $ra, 4($sp) 		# save the return address
		sw $a0, 0($sp) 		# save the current value of n
		addi $sp, $sp, -8 	# move stack pointer
		slti $t0, $a0, 2 	# save 1 iteration, n=0 or n=1; n!=1
		beq $t0, $zero, L1	# not less than 2, calculate n(n-1)!
		addi $v0, $zero, 1	# n=1; n!=1
		jr $ra				# now multiply
		
L1:		addi $a0, $a0, -1	# n = n-1
		
		jal factrl			# now (n-1)!
		
		addi $sp, $sp, 8	# reset the stack pointer
		lw $a0, 0($sp)		# fetch saved (n-1)
		lw $ra, 4($sp)		# fetch return address
		mul $v0, $a0, $v0	# multiply (n)*(n-1)
		jr $ra				# return value n!

############################################
	
	