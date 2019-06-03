import time
import smbus
import sys

bus = smbus.SMBus(1)
address_imu=0x28

data=bus.read_i2c_block_data(address_imu,0)

print("Data: ", data)