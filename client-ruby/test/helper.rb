require 'simplecov'
SimpleCov.root '..'
SimpleCov.add_filter '/test/'
SimpleCov.start

require 'codecov'
SimpleCov.formatter = SimpleCov::Formatter::Codecov
