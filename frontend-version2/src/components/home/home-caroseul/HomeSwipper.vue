<template>
    <swiper :options="swiperOption">
        <swiper-slide v-for="(item, index) in props" :key="index">
        <img :src="item.image" alt="Slide Image">
        <p>{{ item.description }}</p>
        </swiper-slide>
  </swiper>
</template>

<script setup lang="ts">
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/swiper-bundle.css';

const swiperOption = {
  loop: true,
  navigation: true,
  pagination: {
    clickable: true,
  },
  // More options as needed
};

const props = defineProps<{
    
}>
</script>