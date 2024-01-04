<?php
    header('Content-Type: application/json');
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: POST');
    header('Access-Control-Allow-Headers: Content-Type');

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $data = json_decode(file_get_contents('php://input'), true);

        if (isset($data['id']) && isset($data['full_name'])) {
            $url = 'http://localhost:8080/api/fichier';

            $curl = curl_init($url);
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
            curl_setopt($curl, CURLOPT_POST, true);
            curl_setopt($curl, CURLOPT_POSTFIELDS, json_encode($data));

            $response = curl_exec($curl);

            if ($response === false) {
                http_response_code(500);
                echo json_encode(['error' => 'Error saving repository.']);
            } else {
                http_response_code(200);
                echo $response;
            }
        } else {
            http_response_code(400);
            echo json_encode(['error' => 'Invalid request data.']);
        }
    } else {
        http_response_code(405);
        echo json_encode(['error' => 'Method not allowed.']);
    }
?>