package main.leetcode;


import java.util.*;

/**
 * @program: Algorithm
 * @description: Longest Substring Without Repeating Characters(无重复字符的最长子串)
 * @description: 这是一个滑动窗口的问题。通过int[]或者是HashMap维护一个没有重复字符的滑动窗口。窗口的右边界是当前遍历到字符的当前位置，并指定一个变量left为窗口的左边界。
 * 如果当前遍历到的字符不在滑动窗口内，把字符添加到滑动窗口内，并扩大右边界。如果在窗口内，移除窗口内的重复字符（left指针当前的位置就是重复字符的位置），并left右移（left+1）。
 * 最终窗口的大小就是最终结果。
 *
 * int[]、HashMap的长度设定为256是因为ASCII码只能表示256个字符。键盘只能表示128个字符。
 *HashMap设置初始容量的合理值有助于提升性能。当HashMap内部维护的hash表的容量达到了75%（默认情况下），会触发rehash（重建hash表）。
 * 如果没有设置初始化容量的大小，随着元素的不断增加，HashMap会发生多次扩容。HashMap的扩容机制是重建hash表，是非常影响性能的。
 *
 * @author: lubo
 * @create: 2019-06-20 14:57
 **/
public class LeetCode3 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] sArr = s.toCharArray();
        int[] hash = new int[256];
        int left = 0, result = 1;
        for (int r = 0; r < sArr.length; ++r) {
            hash[sArr[r]]++;
            while (hash[sArr[r]] != 1) {
                hash[sArr[left]]--;
                left++;
            }
            result = Math.max(result, r - left + 1);
        }
        return result;
    }

    public int lengthOfLongestSubstring1(String s) {
        int[] m = new int[256];
        Arrays.fill(m, -1);
        int res = 0, left = -1;
        for (int i = 0; i < s.length(); ++i) {
            left = Math.max(left, m[s.charAt(i)]);
            m[s.charAt(i)] = i;
            res = Math.max(res, i - left);
        }
        return res;
    }

    public int lengthOfLongestSubstring2(String s) {
        int res = 0, left = 0, right = 0;
        HashSet<Character> t = new HashSet<Character>();
        while (right < s.length()) {
            if (!t.contains(s.charAt(right))) {
                t.add(s.charAt(right++));
                res = Math.max(res, t.size());
            } else {
                t.remove(s.charAt(left++));
            }
        }
        return res;
    }

    public int lengthOfLongestSubstring3(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))) {
                // 此处是根据角标j来遍历，一个一个的往里存
                set.add(s.charAt(j++));
                //注意这里为什么是j-1而不是j-i+1呢？因为j已经++了
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    public int lengthOfLongestSubstring4(String s) {
        int n = s.length(), ans = 0;
        // Hashmap中存放的是 字符-索引
        Map<Character, Integer> map = new HashMap<>();
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                // 如果map里已经有这个index[j]处的char，将i赋成j+1。
                i = Math.max(map.get(s.charAt(j)) + 1, i);
            }
            // 注意这里的max，适用于“abba”这种情况，“a”虽然存在于map中了，但i也不移动
            ans = Math.max(ans, j - i + 1);
            // 将char加入哈希表中，value为索引值j。
            map.put(s.charAt(j), j);
        }
        return ans;
    }

    public static void main(String[] args) {
        LeetCode3 code3 = new LeetCode3();
        String s = "abcabcbb";
        int res = code3.lengthOfLongestSubstring1(s);
        System.out.println(res);
        int res2 = code3.lengthOfLongestSubstring2(s);
        System.out.println(res2);
        int res3 = code3.lengthOfLongestSubstring3(s);
        System.out.println(res3);
        int res4 = code3.lengthOfLongestSubstring4(s);
        System.out.println(res4);
    }



}
