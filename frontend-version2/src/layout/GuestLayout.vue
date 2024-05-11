<template>
    <header>
        <HomeNavTop></HomeNavTop>
    </header>
    <slot name="content"></slot>
    <footer>
        <HomeFooter></HomeFooter>
    </footer>
</template>

<script setup lang="ts">
import HomeFooter from '@/components/home/HomeFooter.vue';
import HomeNavTop from '@/components/home/HomeNavTop.vue';


</script>
