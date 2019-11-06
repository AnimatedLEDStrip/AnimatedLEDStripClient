require 'simplecov'
require 'codecov'

formatter = SimpleCov::Formatter::Codecov
formatter.format(SimpleCov::ResultMerger.merged_result)
