<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel frontend\models\RepairsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Repairs';
//$this->params['breadcrumbs'][] = $this->title;
?>
<div class="repairs-index">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Create Repairs', ['create'], ['class' => 'btn btn-secondary']) ?>
    </p>

    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            //['class' => 'yii\grid\SerialColumn'],

            //'id',
            'kilometers',
            'repairdate',
            'repairdescription',
            'state',
            'repairtype',
            'carId',
            //'contributorId',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>


</div>
