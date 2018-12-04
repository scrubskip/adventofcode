#!/usr/bin/env python
# 

from parse import *

def main():
    guard_logs = open("day4input.txt", "r")
    guard_records = process_logs(guard_logs.readlines().sort())

def process_logs(sorted_lines):
    current_guard = None

    for line in sorted_lines:
        

    return []

class GuardRecord:
    def __init__(self, guard_id, date):
        self.guard_id = guard_id
        self.date = date
        self.sleep = [0 for _ in range(60)]
        self.last_sleep = -1
    
    def wakeup(self, minute):
        if (self.last_sleep > 0):
            for minute in range(self.last_sleep, minute):
                self.sleep[minute] = 1

    def go_to_sleep(self, minute):
        self.last_sleep = minute
        self.sleep[minute] = 1

if __name__== "__main__":
    print main()