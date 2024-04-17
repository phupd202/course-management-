<template>
   <SelectComponent 
            :items="classrooms" 
            label="Vui lòng nhập tên lớp học" 
            value-field="classroomId" 
            display-field="name" 
            v-model:model-value="classroomId" 
            @update:model-value="getClassroomId">
    </SelectComponent>
</template>

<script setup lang="ts">
import SelectComponent from '@/components/select/SelectComponent.vue';
import { ref } from 'vue';

interface Classroom {
    classroomId: number, 
    name: string, 
    size: number
}

const classroomId = ref<number>(-1);
const classrooms = ref<Classroom[]>([
    {
        classroomId: 1, 
        name: "Lập trình Java", 
        size: 34
    }, 
    {
        classroomId: 2, 
        name: "Lập trình Python", 
        size: 25
    }
])

const getClassroomId = (sentClassroomId: number) => {
    classroomId.value = sentClassroomId
    alert("Dữ liệu nhận được từ emit: "+ classroomId.value)
}
</script>