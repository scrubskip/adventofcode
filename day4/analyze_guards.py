#!/usr/bin/env python
#

from parse import compile


def main():
    guard_logs = open("day4input.txt", "r")

    guard_records = {}

    for guard_record in process_logs(guard_logs.readlines()):
        if (guard_record.guard_id not in guard_records):
            guard_records[guard_record.guard_id] = []
        guard_records[guard_record.guard_id].append(guard_record)

    most_sleep = 0
    most_sleep_id = None
    for guard_id in guard_records.keys():
        guard_sleep = reduce(
            lambda x, y: x+y, map(lambda x: x.get_sleep_count(), guard_records[guard_id]))
        if (guard_sleep > most_sleep):
            most_sleep = guard_sleep
            most_sleep_id = guard_id

    if (most_sleep_id is not None):
        print "sleep_id ", most_sleep_id, ", ", most_sleep
        print('\n'.join(map(str, guard_records[most_sleep_id])))
        print(find_likely_sleep_minute(guard_records[most_sleep_id]))
        return most_sleep_id * find_likely_sleep_minute(guard_records[most_sleep_id])

    return None


def process_logs(lines):
    p = compile("[{date:ti}] {event}")
    g = compile("Guard #{guard_id:d} begins shift")
    lines.sort()
    current_guard = None
    guard_records = []

    for line in lines:
        data = p.parse(line)
        if (data is not None):
            event_str = data['event']
            event_date = data['date']
            if (event_str.endswith("begins shift")):
                # If this is a guard event, make a new guard record
                if (current_guard is not None):
                    current_guard.end_shift()

                guard_data = g.parse(event_str)
                current_guard = GuardRecord(guard_data['guard_id'], event_date)
                guard_records.append(current_guard)

            if (event_str.startswith("wakes up")):
                current_guard.wakeup(event_date.minute)

            if (event_str.startswith("falls asleep")):
                current_guard.go_to_sleep(event_date.minute)

    return guard_records


def find_likely_sleep_minute(guard_records):
    minute_count = [0 for _ in range(60)]
    for i in range(60):
        for guard_record in guard_records:
            if (guard_record.is_sleep(i)):
                minute_count[i] += 1

    max_count = 0
    max_index = -1
    for i in range(60):
        if (minute_count[i] > max_count):
            max_index = i
            max_count = minute_count[i]
    print minute_count
    print "Max count {0}, index {1}".format(max_count, max_index)
    return max_index


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

    def end_shift(self):
        if (self.last_sleep > 0):
            self.wakeup(60)

    def get_sleep_count(self):
        return self.sleep.count(1)

    def is_sleep(self, minute):
        return self.sleep[minute] == 1

    def __str__(self):
        return "{0} {1} {2}".format(self.guard_id, self.date, self.sleep)


if __name__ == "__main__":
    print main()
