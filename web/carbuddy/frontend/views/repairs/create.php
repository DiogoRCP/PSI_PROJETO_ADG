<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */
/* @var $modelCars frontend\models\Cars */
/* @var $modelUsers backend\models\Users */
/* @var $modelContributor frontend\models\Contributors */

$this->title = 'Add Repair';
//$this->params['breadcrumbs'][] = ['label' => 'Repairs', 'url' => ['index']];
//$this->params['breadcrumbs'][] = $this->title;
?>
<div class="repairs-create">
    <div class="repairs-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', "javascript:history.back()", ['class' => 'btn btn-light']) ?>
        </p>
        <?= $this->render('_form', [
            'model' => $model, 'modelCars' => $modelCars
        ]) ?>
    </div>
</div>
