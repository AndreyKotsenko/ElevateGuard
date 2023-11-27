include .env

TEMPLATE_FILE = const.template
OUTPUT_FILE = app/src/main/java/com/akotsenko/elevateguard/Const.kt

replace_values:
	sed 's/HTTP_ENV/$(HTTP_ENV)/g' $(TEMPLATE_FILE) > $(OUTPUT_FILE)