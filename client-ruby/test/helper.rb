require 'simplecov'
SimpleCov.root '../..'
SimpleCov.start

require 'codecov'
SimpleCov.formatter = SimpleCov::Formatter::Codecov
