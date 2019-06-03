#!/usr/bin/python

import smbus
import time

bus = smbus.SMBus(1)

DEVICE_ADDRESS = 0x04

def transmit():
    bus.write_i2c_block_data(DEVICE_ADDRESS, 1, [1,2,3,4])

def format(val1,val2,val3,neg):
    if len(str(val3))==1:val3 = "0" + str(val3)
    if neg==1:
        return str((val1+val2)*-1) + "." + str(val3)
    else:
        return str(val1+val2) + "." + str(val3)

def receive():
    block = bus.read_i2c_block_data(DEVICE_ADDRESS, 0, 13)
    print("Yaw: " + format(block[0],block[1],block[2],block[3]),"Roll: " + format(block[4],block[5],block[6],block[7]),"Pitch: " + format(block[8],block[9],block[10],block[11]))

while 1:
    time.sleep(1)
    transmit()
    time.sleep(1)
    receive()