#!/Users/moomindani/.rbenv/shims/ruby
# -*- coding: utf-8 -*-

# Ruby template for Google Code Jam

require 'benchmark'

class Solution

  def solve(dataset)
    File.open(dataset, 'r') do |input|
      File.open(dataset.sub(/\.in/, '.out'), 'w') do |output|
        test_cases = input.readline.to_i
        1.upto(test_cases) do |test_case|
          # write answer
          output << %Q{Case ##{test_case}: 1\n}
        end
      end
    end
  end
end

problem = Solution.new
Benchmark.bm do |x|
  x.report('small') { problem.solve('A-small-practice.in') }
  x.report('large') { problem.solve('A-large-practice.in') }
end
