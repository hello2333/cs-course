#
# @lc app=leetcode id=179 lang=python3
#
# [179] Largest Number
#

# @lc code=start

from functools import cmp_to_key
from typing import List

class Solution:
    # by myself
    def largestNumber1(self, nums: List[int]) -> str:
        num_str = ""
        for num in nums:
            num_str += str(num)
        sort(num_str)
    # by gpt
    def largestNumber1(self, nums: List[int]) -> str:
        num_strs = list(map(str, nums))

        # 1. sort num_strs by combination value, eg, "9"+"34" > "34" + "9"
        # 2. join num_strs
        def compare_combination(x, y):
            if x + y > y + x:
                return -1
            if x + y < y + x:
                return 1
            return 0
        num_strs.sort(key=cmp_to_key(compare_combination))
        result = "".join(num_strs)
        if len(result) == 0:
            return "0"
        return result

# @lc code=end

