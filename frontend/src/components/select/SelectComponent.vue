<template>
  <div class="mb-3">
    <label for="nameCourse" class="form-label">{{ props.label }}</label>
    <select class="form-select" id="nameCourse" v-model="selectedValue" @change="handleChange">
      <option value="" selected disable hidden>Vui lòng chọn</option>
      <option v-for="(item, index) in items" :key="index" :value="item[props.valueField]"> {{ item[props.displayField] }} </option>
    </select>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import { ref } from 'vue';

interface Item {
  [key: string] : any;
}

const props = defineProps<{
  items: Item[], 
  label: string, 
  valueField: string, 
  displayField: string
}>();

const selectedValue = ref<string>('');

const emit = defineEmits(['update:modelValue']);

const handleChange = () => {
  if (selectedValue.value) {
    emit("update:modelValue", selectedValue.value);
  } else {
    console.error('Please select a valid value');
  }
}
</script>

