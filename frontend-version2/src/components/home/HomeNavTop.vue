<template>
     <div class="nav-header bg-white shadow-xs border-0">
        <div class="nav-top">
            <a href="index.html"><i class="feather-zap text-success display1-size me-2 ms-0"></i><span
                    class="d-inline-block fredoka-font ls-3 fw-600 text-current font-xxl logo-text mb-0">Sociala.
                </span> </a>
        </div>

        <div class="search">
            <form action="#" class="float-left header-search" style="display: flex;" id="form-search">
                <div class="form-group mb-0 icon-input search-div" style="width: 600px;">
                    <i class="feather-search font-sm text-grey-400"></i>
                    <input type="text" placeholder="Start typing to search.." style="width: 80%;" class="bg-grey border-0 lh-32 pt-2 pb-2 ps-5 pe-3 font-xssss fw-500 rounded-xl w350 theme-dark-bg">
                    <button type="submit" class="btn-search center-menu-icon">Tìm kiếm</button>
                </div>
            </form>
        </div>

        <div class = "right-link float-right" >
            <a href="default.html" class="p-2 text-center ms-3 menu-icon center-menu-icon"><i
                    class="feather-home font-lg alert-primary btn-round-lg theme-dark-bg text-current "></i></a>
            <a href="shop-2.html" class="p-2 text-center ms-0 menu-icon center-menu-icon"><i
                    class="feather-shopping-bag font-lg bg-greylight btn-round-lg theme-dark-bg text-grey-500 "></i></a>
        </div>  
    </div>
</template>

<script setup lang="ts"></script>
<style scoped>
/* @import url('../../assets/css/style.css'); */
@import url('../../assets/css/feather.css');
@import url('../../assets/css/style.css');
@import url('../../assets/css/themify-icons.css');

.seach {
    display: flex;
    flex-direction: row;
    width: 800px;
}

#search-div {
    width: 100%
}

.btn-search {
    margin: auto;
    width: 100px;
    height: 45px;
    border: none;
    border-radius: 15px;
    background-color: #1E74FD;
    color: white; 
    font-weight: bold; 
    cursor: pointer; 
    transition: background-color 0.3s; 
    margin-left: 20px;
}

.btn-search:hover {
    background-color: #4799FF;
}

.div-btn {
    display: flex;
    justify-content: center;
    align-items: center;
}

.nav-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between
}

</style>
